package betr.intern.chainsaw.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import betr.intern.chainsaw.model.domain.User;
import betr.intern.chainsaw.repository.UserRepository;
import betr.intern.chainsaw.service.UserService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.json.JsonMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Testcontainers
@SpringBootTest
public class UserGraphQlControllerTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void setProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExecutionGraphQlService graphQlService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        this.userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetUserById_GraphQLQuery_Success() throws Exception {
        final User user = new User("456", "GraphQL User", "graphql@gmail.com");
        this.userService.create(user);

        final String graphQLQuery = "{\"query\": \"query { getUserById(id: \\\"456\\\") { name email } }\"}";

        final var responseAsString = this.mockMvc
                .perform(
                        post("/graphql").contentType(MediaType.APPLICATION_JSON).content(graphQLQuery))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // graphql response has data and function name method
        final var root = jsonMapper.readTree(responseAsString);

        org.assertj.core.api.Assertions.assertThat(
                        root.path("data").path("getUserById").path("name").asText())
                .isEqualTo("GraphQL User");
        org.assertj.core.api.Assertions.assertThat(
                        root.path("data").path("getUserById").path("email").asText())
                .isEqualTo("graphql@gmail.com");
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetListUserByIdEndpointAccessMap_GraphQLSubscription_Success() {
        var graphQlTester =
                org.springframework.graphql.test.tester.ExecutionGraphQlServiceTester.create(graphQlService);

        final String graphQLSubscription = """
            subscription Stats {
                stats {
                    name
                    viewRecordDTO {
                        viewCount
                        lastUpdated
                    }
                }
            }
        """;

        Flux<List> responseFlux = graphQlTester
                .document(graphQLSubscription)
                .executeSubscription()
                .toFlux("stats", java.util.List.class);

        StepVerifier.create(responseFlux)
                .consumeNextWith(Assertions::assertNotNull)
                .thenCancel()
                .verify();
    }
}

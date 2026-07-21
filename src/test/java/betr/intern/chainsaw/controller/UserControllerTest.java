package betr.intern.chainsaw.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Testcontainers
@SpringBootTest
class UserControllerTest {

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.springframework.graphql.ExecutionGraphQlService graphQlService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        this.userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void testListUsers_ReturnsUsers() throws Exception {
        final User u1 = new User("1", "Alice", "alice@gmail.com");
        final User u2 = new User("2", "Bob", "bob@gmail.com");
        this.userService.create(u1);
        this.userService.create(u2);

        this.mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alice")))
                .andExpect(jsonPath("$[1].name", is("Bob")));
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void testListUsers_ForbiddenForInvalidRole() throws Exception {
        this.mockMvc.perform(get("/users")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUser_Success() throws Exception {
        final User user = new User("123", "ToDelete", "delete@gmail.com");
        this.userService.create(user);

        this.mockMvc
                .perform(delete("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id=123 deleted"));

        assertNull(this.userService.findById(user.getId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testResetListUserByIdEndpointAccessMap_Success() throws Exception {
        this.mockMvc
                .perform(put("/stats/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stats reset"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetUserById_GraphQLQuery_Success() throws Exception {
        final User user = new User("456", "GraphQL User", "graphql@gmail.com");
        this.userService.create(user);

        final String graphQLQuery = "{\"query\": \"query { getUserById(id: \\\"456\\\") { name email } }\"}";

        this.mockMvc
                .perform(
                        post("/graphql").contentType(MediaType.APPLICATION_JSON).content(graphQLQuery))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.getUserById.name", is("GraphQL User")))
                .andExpect(jsonPath("$.data.getUserById.email", is("graphql@gmail.com")));
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

package betr.intern.chainsaw.controller;

import betr.intern.chainsaw.model.domain.User;
import betr.intern.chainsaw.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@AutoConfigureGraphQlTester
public class UserGraphQlControllerTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void setProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetUserById_GraphQLQuery_Success() {
        userRepository.save(new User("456", "GraphQL User", "graphql@gmail.com"));

        graphQlTester
                .documentName("get-user")
                .execute()
                .path("getUserById.name")
                .entity(String.class)
                .isEqualTo("GraphQL User");

        graphQlTester
                .documentName("get-user")
                .execute()
                .path("getUserById.email")
                .entity(String.class)
                .isEqualTo("graphql@gmail.com");
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetListUserByIdEndpointAccessMap_GraphQLSubscription_Success() {
        Flux<Object> payloadFlux = this.graphQlTester
                .documentName("get-stats")
                .executeSubscription()
                .toFlux("stats", Object.class);

        assertThat(payloadFlux).isNotNull();

        reactor.test.StepVerifier.create(payloadFlux)
                .assertNext(Assertions::assertNotNull)
                .thenCancel()
                .verify();
    }
}

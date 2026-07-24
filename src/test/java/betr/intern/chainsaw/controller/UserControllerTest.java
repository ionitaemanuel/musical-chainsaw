package betr.intern.chainsaw.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import betr.intern.chainsaw.generated.model.UserResponse;
import betr.intern.chainsaw.model.domain.User;
import betr.intern.chainsaw.repository.UserRepository;
import betr.intern.chainsaw.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.json.JsonMapper;

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

    private final JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

        final var responseAsString = this.mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var response = jsonMapper.readValue(responseAsString, new TypeReference<List<UserResponse>>() {});

        assertThat(response).hasSize(2);
        assertThat(response).extracting(UserResponse::getName).containsExactly(u1.getName(), u2.getName());
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

        assertThat(this.userService.findById(user.getId())).isNull();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testResetListUserByIdEndpointAccessMap_Success() throws Exception {
        this.mockMvc
                .perform(put("/stats/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stats reset"));
    }
}

package betr.intern.chainsaw.controller.rest;

import betr.intern.chainsaw.generated.controller.rest.UsersApi;
import betr.intern.chainsaw.generated.model.UserRequest;
import betr.intern.chainsaw.generated.model.UserResponse;
import betr.intern.chainsaw.mapper.UserMapper;
import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.service.UserService;
import betr.intern.chainsaw.service.UserStatsService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController implements UsersApi {
    private final UserService userService;
    private final UserStatsService userStatsService;
    private final UserMapper userMapper;
    private final StatsEndpoint statsEndpoint;

    public UserController(
            final UserService userService,
            final UserStatsService userStatsService,
            final UserMapper userMapper,
            StatsEndpoint statsEndpoint) {
        this.userService = userService;
        this.userStatsService = userStatsService;
        this.userMapper = userMapper;
        this.statsEndpoint = statsEndpoint;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/users")
    public List<UserResponse> listUsers() {
        return userService.findAll().stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @QueryMapping
    public UserResponse getUserById(@Argument final UUID id) {
        return userMapper.toResponse(userService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<UserResponse> updateUser(UUID userId, UserRequest userRequest) {
        final User user = userMapper.toEntity(userRequest);
        final User updatedUser = userService.update(user, userId);
        final UserResponse responseBody = userMapper.toResponse(updatedUser);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable final UUID id) {
        return userService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/stats/reset")
    public String resetListUserByIdEndpointAccessMap() {
        userStatsService.resetListUserByIdEndpointAccessMap();
        return "Stats reset";
    }
}

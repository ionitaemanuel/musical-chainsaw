package betr.intern.chainsaw.controller;

import betr.intern.chainsaw.generated.model.UserResponse;
import betr.intern.chainsaw.mapper.UserMapper;
import betr.intern.chainsaw.service.UserService;
import betr.intern.chainsaw.service.UserStatsService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;
    private final UserStatsService userStatsService;
    private final UserMapper userMapper;

    public UserController(
            final UserService userService, final UserStatsService userStatsService, final UserMapper userMapper) {
        this.userService = userService;
        this.userStatsService = userStatsService;
        this.userMapper = userMapper;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/users")
    public List<UserResponse> listUsers() {
        return userService.findAll().stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable final String id) {
        return userService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/stats/reset")
    public String resetListUserByIdEndpointAccessMap() {
        userStatsService.resetListUserByIdEndpointAccessMap();
        return "Stats reset";
    }
}

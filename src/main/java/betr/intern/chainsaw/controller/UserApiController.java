package betr.intern.chainsaw.controller;

import betr.intern.chainsaw.mapper.UserMapper;
import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.model.ViewRecord;
import betr.intern.chainsaw.model.dto.UserRecord;
import betr.intern.chainsaw.service.UserService;
import betr.intern.chainsaw.service.UserStatsService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController {
    private final UserService userService;
    private final UserStatsService userStatsService;
    private final UserMapper userMapper;

    public UserApiController(
            final UserService userService, final UserStatsService userStatsService, final UserMapper userMapper) {
        this.userService = userService;
        this.userStatsService = userStatsService;
        this.userMapper = userMapper;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/users")
    public List<UserRecord> listUsers() {
        return userService.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/users/{id}")
    public UserRecord listUserById(@PathVariable final UUID id) {
        return userMapper.toDto(userService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public UserRecord updateUser(@PathVariable final UUID id, @RequestBody final UserRecord body) {
        final User user = userMapper.toEntity(body);
        return userMapper.toDto(userService.update(user, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable final UUID id) {
        return userService.deleteById(id);
    }

    @GetMapping("/stats")
    public Map<String, ViewRecord> getListUserByIdEndpointAccessMap() {
        final Map<UUID, ViewRecord> map = userStatsService.getListUserByIdEndpointAccessMap();
        return userService.findAllById(map.keySet()).stream()
                .collect(Collectors.toMap(User::getName, user -> map.get(user.getId())));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/stats/reset")
    public String resetListUserByIdEndpointAccessMap() {
        userStatsService.resetListUserByIdEndpointAccessMap();
        return "Stats reset";
    }
}

package betr.intern.chainsaw.controller;

import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.model.dto.UserRecord;
import betr.intern.chainsaw.service.UserService;
import betr.intern.chainsaw.service.UserStatsService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController {
    private final UserService userService;
    private final UserStatsService userStatsService;

    public UserApiController(final UserService userService, final UserStatsService userStatsService) {
        this.userService = userService;
        this.userStatsService = userStatsService;
    }

    @GetMapping("/users")
    public List<User> listUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User listUserById(@PathVariable(value = "id") final UUID id) {
        return userService.findById(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") final UUID id, @RequestBody final UserRecord body) {
        return userService.update(new User(body.name(), body.email()), id);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable(value = "id") final UUID id) {
        userService.deleteById(id);
        return "User with id " + id + " deleted";
    }

    @GetMapping("/stats")
    public Map<String, Integer> getListUserByIdEndpointAccessMap() {
        final Map<UUID, Integer> map = userStatsService.getListUserByIdEndpointAccessMap();
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> userService.findById(entry.getKey()).getName(), Map.Entry::getValue));
    }

    @PutMapping("/stats/reset")
    public String resetListUserByIdEndpointAccessMap() {
        userStatsService.resetListUserByIdEndpointAccessMap();
        return "Stats reset";
    }
}

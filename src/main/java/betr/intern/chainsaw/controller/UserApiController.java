package betr.intern.chainsaw.controller;

import betr.intern.chainsaw.mapper.UserToDtoMapper;
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
    private final UserToDtoMapper userToDtoMapper;

    public UserApiController(
            final UserService userService,
            final UserStatsService userStatsService,
            final UserToDtoMapper userToDtoMapper) {
        this.userService = userService;
        this.userStatsService = userStatsService;
        this.userToDtoMapper = userToDtoMapper;
    }

    @GetMapping("/users")
    public List<UserRecord> listUsers() {
        return userService.findAll().stream().map(userToDtoMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserRecord listUserById(@PathVariable(value = "id") final UUID id) {
        return userToDtoMapper.toDto(userService.findById(id));
    }

    @PutMapping("/users/{id}")
    public UserRecord updateUser(@PathVariable(value = "id") final UUID id, @RequestBody final UserRecord body) {
        return userToDtoMapper.toDto(userService.update(new User(body.name(), body.email()), id));
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable(value = "id") final UUID id) {
        userService.deleteById(id);
        return "User with id " + id + " deleted";
    }

    @GetMapping("/stats")
    public Map<String, Integer> getListUserByIdEndpointAccessMap() {
        final Map<UUID, Integer> map = userStatsService.getListUserByIdEndpointAccessMap();
        return userService.findAllById(map.keySet()).stream()
                .collect(Collectors.toMap(User::getName, user -> map.get(user.getId())));
    }

    @PutMapping("/stats/reset")
    public String resetListUserByIdEndpointAccessMap() {
        userStatsService.resetListUserByIdEndpointAccessMap();
        return "Stats reset";
    }
}

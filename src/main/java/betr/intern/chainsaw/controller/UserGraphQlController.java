package betr.intern.chainsaw.controller;

import betr.intern.chainsaw.generated.model.UserResponse;
import betr.intern.chainsaw.mapper.StatsMapper;
import betr.intern.chainsaw.mapper.UserMapper;
import betr.intern.chainsaw.model.domain.ViewRecord;
import betr.intern.chainsaw.model.dto.StatsDTO;
import betr.intern.chainsaw.service.UserService;
import betr.intern.chainsaw.service.UserStatsService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class UserGraphQlController {

    private final UserService userService;
    private final UserStatsService userStatsService;
    private final UserMapper userMapper;
    private final StatsMapper statsMapper;

    public UserGraphQlController(
            final UserService userService,
            final UserStatsService userStatsService,
            final UserMapper userMapper,
            final StatsMapper statsMapper) {
        this.userService = userService;
        this.userStatsService = userStatsService;
        this.userMapper = userMapper;
        this.statsMapper = statsMapper;
    }

    @SubscriptionMapping("stats")
    public Flux<List<StatsDTO>> getListUserByIdEndpointAccessMap() {
        return Flux.defer(() -> {
            final Map<String, ViewRecord> map = userStatsService.getListUserByIdEndpointAccessMap();
            final List<StatsDTO> statsList = userService.findAllById(map.keySet()).stream()
                    .map(user -> statsMapper.toDTO(user.getName(), map.get(user.getId())))
                    .collect(Collectors.toList());
            return Flux.just(statsList);
        });
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @QueryMapping
    public UserResponse getUserById(@Argument final String id) {
        return userMapper.toResponse(userService.findById(id));
    }
}

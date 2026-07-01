package betr.intern.chainsaw.aspect;

import betr.intern.chainsaw.service.UserStatsService;
import java.util.Map;
import java.util.UUID;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {
    private final UserStatsService userStatsService;

    public UserAspect(final UserStatsService userStatsService) {
        this.userStatsService = userStatsService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    @AfterReturning("execution(* betr.intern.chainsaw.controller.UserApiController.listUserById(..))")
    private void listUserByIdMethod(final JoinPoint jp) {
        final Object[] args = jp.getArgs();
        final UUID id = (UUID) args[0];
        final Map<UUID, Integer> updatedList = userStatsService.getListUserByIdEndpointAccessMap();
        updatedList.merge(id, 1, Integer::sum);
        userStatsService.setListUserByIdEndpointAccessMap(updatedList);
        logger.info("listUserByIdEndpointAccessMap: {}", userStatsService.getListUserByIdEndpointAccessMap());
    }
}

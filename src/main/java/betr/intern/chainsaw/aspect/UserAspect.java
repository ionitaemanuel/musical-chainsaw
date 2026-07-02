package betr.intern.chainsaw.aspect;

import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.model.ViewRecord;
import betr.intern.chainsaw.service.UserStatsService;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
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
        final Map<UUID, ViewRecord> updatedList = userStatsService.getListUserByIdEndpointAccessMap();
        updatedList.compute(id, (key, existingViewRecord) -> {
            if (existingViewRecord == null) {
                return new ViewRecord(1, OffsetDateTime.now());
            } else {
                return new ViewRecord(existingViewRecord.viewCounter() + 1, OffsetDateTime.now());
            }
        });
        userStatsService.setListUserByIdEndpointAccessMap(updatedList);
        logger.info("listUserByIdEndpointAccessMap: {}", userStatsService.getListUserByIdEndpointAccessMap());
    }

    @AfterReturning(
            pointcut = "execution(* betr.intern.chainsaw.service.UserService.findAll(..))",
            returning = "result")
    private void listInternalUserIds(final JoinPoint jp, final List<User> result) {
        logger.info(
                "listInternalUserIds({})",
                result.stream().map(User::getId).map(Object::toString).collect(Collectors.joining(", ")));
    }
}

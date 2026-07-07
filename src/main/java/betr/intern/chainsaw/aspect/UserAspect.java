package betr.intern.chainsaw.aspect;

import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.model.ViewRecord;
import betr.intern.chainsaw.service.UserStatsService;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {
    private final UserStatsService userStatsService;
    private final ApplicationEventPublisher eventPublisher;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss z", Locale.ENGLISH);

    public UserAspect(final UserStatsService userStatsService, final ApplicationEventPublisher eventPublisher) {
        this.userStatsService = userStatsService;
        this.eventPublisher = eventPublisher;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    @AfterReturning("execution(* betr.intern.chainsaw.controller.rest.UserController.getUserById(..))")
    private void listUserByIdMethod(final JoinPoint jp) {
        final Object[] args = jp.getArgs();
        final UUID id = (UUID) args[0];
        final Map<UUID, ViewRecord> updatedMap = userStatsService.getListUserByIdEndpointAccessMap();
        updatedMap.compute(id, (key, existingViewRecord) -> {
            if (existingViewRecord == null) {
                return new ViewRecord(1, OffsetDateTime.now());
            } else {
                return new ViewRecord(existingViewRecord.viewCount() + 1, OffsetDateTime.now());
            }
        });
        userStatsService.setListUserByIdEndpointAccessMap(updatedMap);
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

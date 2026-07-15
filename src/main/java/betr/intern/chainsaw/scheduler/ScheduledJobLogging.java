package betr.intern.chainsaw.scheduler;

import betr.intern.chainsaw.model.domain.ViewRecord;
import betr.intern.chainsaw.service.UserStatsService;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJobLogging {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledJobLogging.class);
    private final UserStatsService userStatsService;

    public ScheduledJobLogging(final UserStatsService userStatsService) {
        this.userStatsService = userStatsService;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void scheduleJobCurrentStats() {
        final Map<String, ViewRecord> mapOfIdsAndStats = userStatsService.getListUserByIdEndpointAccessMap();
        mapOfIdsAndStats.forEach((id, stats) -> {
            logger.info("user with id={} has been searched for {} times\n\n", id, stats.viewCount());
        });
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void scheduleJobFilterStats() {
        final Map<String, ViewRecord> filteredMap =
                userStatsService.getListUserByIdEndpointAccessMap().entrySet().stream()
                        .filter(entry -> entry.getValue()
                                .lastUpdated()
                                .isAfter(OffsetDateTime.now().minusMinutes(2)))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        userStatsService.setListUserByIdEndpointAccessMap(filteredMap);
        logger.info("users not searched in the last 2 minutes removed from stats");
    }
}

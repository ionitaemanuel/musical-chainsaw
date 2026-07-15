package betr.intern.chainsaw.model.domain;

import java.time.OffsetDateTime;

public record ViewRecord(Integer viewCount, OffsetDateTime lastUpdated) {}

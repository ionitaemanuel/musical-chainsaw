package betr.intern.chainsaw.model;

import java.time.OffsetDateTime;

public record ViewRecord(Integer viewCount, OffsetDateTime lastUpdated) {}

package betr.intern.chainsaw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record ViewRecord(
        @JsonProperty("viewCount") Integer viewCount,
        @JsonProperty("lastUpdated") OffsetDateTime lastUpdated) {}

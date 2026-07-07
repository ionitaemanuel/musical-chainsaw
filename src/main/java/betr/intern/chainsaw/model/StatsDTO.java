package betr.intern.chainsaw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StatsDTO(
        @JsonProperty("name") String name,
        @JsonProperty("viewRecordDTO") ViewRecordDTO viewRecordDTO) {}

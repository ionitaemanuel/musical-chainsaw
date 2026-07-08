package betr.intern.chainsaw.mapper;

import betr.intern.chainsaw.model.domain.ViewRecord;
import betr.intern.chainsaw.model.dto.StatsDTO;
import betr.intern.chainsaw.model.dto.ViewRecordDTO;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss", Locale.ENGLISH).withZone(ZoneId.of("UTC"));

    @Mapping(target = "viewRecordDTO", source = "record")
    StatsDTO toDTO(String name, ViewRecord record);

    @Mapping(target = "lastUpdated", expression = "java(mapLastUpdated(record.lastUpdated()))")
    ViewRecordDTO toDTO(ViewRecord record);

    default String mapLastUpdated(OffsetDateTime lastUpdated) {
        return lastUpdated.format(DATE_TIME_FORMATTER);
    }
}

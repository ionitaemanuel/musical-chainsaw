package betr.intern.chainsaw.mapper;

import betr.intern.chainsaw.model.ViewRecord;
import betr.intern.chainsaw.model.ViewRecordDTO;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ViewRecordMapper {
    static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss z", Locale.ENGLISH);

    default ViewRecord toEntity(ViewRecordDTO record) {
        final String lmao = record.lastUpdated();
        final OffsetDateTime time = OffsetDateTime.parse(lmao, DATE_TIME_FORMATTER);
        return new ViewRecord(record.viewCount(), time);
    }

    default ViewRecordDTO toDTO(ViewRecord record) {
        final OffsetDateTime tempDate = record.lastUpdated();
        final String lmao = tempDate.format(DATE_TIME_FORMATTER);
        return new ViewRecordDTO(record.viewCount(), lmao);
    }
}

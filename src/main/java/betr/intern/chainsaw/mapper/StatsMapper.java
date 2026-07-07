package betr.intern.chainsaw.mapper;

import betr.intern.chainsaw.model.StatsDTO;
import betr.intern.chainsaw.model.ViewRecordDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    StatsDTO toDTO(String name, ViewRecordDTO record);
}

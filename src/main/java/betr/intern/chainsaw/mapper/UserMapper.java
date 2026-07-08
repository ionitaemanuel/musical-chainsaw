package betr.intern.chainsaw.mapper;

import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.model.dto.UserRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRecord toDto(User user);

    User toEntity(UserRecord record);
}

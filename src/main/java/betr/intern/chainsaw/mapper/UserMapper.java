package betr.intern.chainsaw.mapper;

import betr.intern.chainsaw.generated.model.UserRequest;
import betr.intern.chainsaw.generated.model.UserResponse;
import betr.intern.chainsaw.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);

    User toEntity(UserRequest request);
}

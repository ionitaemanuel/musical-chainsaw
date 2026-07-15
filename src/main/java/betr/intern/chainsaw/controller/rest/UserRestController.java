package betr.intern.chainsaw.controller.rest;

import betr.intern.chainsaw.generated.controller.rest.UsersApi;
import betr.intern.chainsaw.generated.model.UserRequest;
import betr.intern.chainsaw.generated.model.UserResponse;
import betr.intern.chainsaw.mapper.UserMapper;
import betr.intern.chainsaw.model.domain.User;
import betr.intern.chainsaw.service.UserService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController implements UsersApi {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<UserResponse> updateUser(final UUID userId, final UserRequest userRequest) {
        final User user = userMapper.toEntity(userRequest);
        final User updatedUser = userService.update(user, userId);
        final UserResponse responseBody = userMapper.toResponse(updatedUser);
        return ResponseEntity.ok(responseBody);
    }
}

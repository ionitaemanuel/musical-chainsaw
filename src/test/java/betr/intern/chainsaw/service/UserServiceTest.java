package betr.intern.chainsaw.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import betr.intern.chainsaw.model.domain.User;
import betr.intern.chainsaw.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

@ExtendWith(MockitoExtension.class)
@Import({UserService.class, UserRepository.class})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;
    private final String userId = "123";
    private final String email = "lol@lolle.com";

    @BeforeEach
    void setUp() {
        sampleUser = new User(userId, "lols", email);
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(sampleUser));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findById_ShouldReturnNull_WhenUserDoesNotExist() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User result = userService.findById(userId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        List<User> result = userService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAllById() {
        Set<String> ids = Set.of(userId);
        when(userRepository.findAllById(ids)).thenReturn(List.of(sampleUser));

        List<User> result = userService.findAllById(ids);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAllById(ids);
    }

    @Test
    void create() {
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        User result = userService.create(new User("John Doe", email));

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update_ShouldUpdateAndSave_WhenIdsMatch() {
        User updatedInfo = new User("Updated Name", "updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.update(updatedInfo, userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update_ShouldReturnPersistedUserWithoutSaving_WhenIdsDoNotMatch() {
        User corruptedUser = new User("wrong", "John Doe", email);
        when(userRepository.findById(userId)).thenReturn(Optional.of(corruptedUser));

        User result = userService.update(sampleUser, userId);

        assertEquals(corruptedUser, result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteById_ShouldDelete_WhenUserExists() {
        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        String result = userService.deleteById(userId);

        assertEquals(String.format("User with id=%s deleted", userId), result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteById_ShouldReturnWarning_WhenUserDoesNotExist() {
        when(userRepository.existsById(userId)).thenReturn(false);

        String result = userService.deleteById(userId);

        assertEquals(String.format("User with id=%s did not exist to begin with", userId), result);
        verify(userRepository, never()).deleteById(userId);
    }
}

package betr.intern.chainsaw.service;

import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(final UUID id) {
        return userRepository.findById(id).orElseGet(() -> null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllById(final Set<UUID> ids) {
        return userRepository.findAllById(ids);
    }

    @Transactional
    public User create(final User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(final User user, final UUID id) {
        final User userPersisted = findById(id);
        if (!Objects.equals(userPersisted.getId(), id)) {
            return userPersisted;
        }
        final User newUser = User.ofUser(userPersisted.getId(), user);

        return userRepository.save(newUser);
    }

    @Transactional
    public void deleteById(final UUID id) {
        userRepository.deleteById(id);
    }
}

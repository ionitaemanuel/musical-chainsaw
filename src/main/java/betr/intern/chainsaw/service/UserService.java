package betr.intern.chainsaw.service;

import betr.intern.chainsaw.model.domain.User;
import betr.intern.chainsaw.repository.UserMongoRepository;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserMongoRepository userRepository;

    public UserService(final UserMongoRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(final String id) {
        return userRepository.findById(id).orElseGet(() -> null);
    }

    public User findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllById(final Set<String> ids) {
        return userRepository.findAllById(ids);
    }

    @Transactional
    public User create(final User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(final User user, final String id) {
        final User userPersisted = findById(id);
        if (!Objects.equals(userPersisted.getId(), id)) {
            return userPersisted;
        }
        final User newUser = User.ofUser(userPersisted.getId(), user);

        return userRepository.save(newUser);
    }

    @Transactional
    public String deleteById(final String id) {
        if (!userRepository.existsById(id)) {
            return String.format("User with id=%s did not exist to begin with", id);
        }
        userRepository.deleteById(id);
        return String.format("User with id=%s deleted", id);
    }
}

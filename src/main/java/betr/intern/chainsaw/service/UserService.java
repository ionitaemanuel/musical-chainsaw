package betr.intern.chainsaw.service;

import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseGet(() -> null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user, UUID id) {
        var userPersisted = findById(id);
        if (!Objects.equals(userPersisted.getId(), id)) {
            return userPersisted;
        }
        BeanUtils.copyProperties(user, userPersisted, "id");

        return userRepository.save(userPersisted);
    }

    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}

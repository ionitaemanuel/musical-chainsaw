package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}

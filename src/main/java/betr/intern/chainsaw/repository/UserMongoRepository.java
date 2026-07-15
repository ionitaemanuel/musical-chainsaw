package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}

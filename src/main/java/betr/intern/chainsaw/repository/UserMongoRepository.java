package betr.intern.chainsaw.repository;

import betr.intern.chainsaw.model.domain.User;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, UUID> {
    User findByEmail(String email);
}

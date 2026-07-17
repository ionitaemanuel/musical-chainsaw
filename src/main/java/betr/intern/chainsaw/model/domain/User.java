package betr.intern.chainsaw.model.domain;

import java.time.Instant;
import org.hibernate.annotations.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Audited
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public User() {}

    public User(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public User(final String id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static User ofUser(final String id, final User user) {
        return new User(id, user.getName(), user.getEmail());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}

package betr.intern.chainsaw.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    public User() {}

    public User(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public User(final UUID id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static User ofUser(final UUID id, final User user) {
        return new User(id, user.getName(), user.getEmail());
    }

    public UUID getId() {
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

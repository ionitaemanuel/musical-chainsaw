package betr.intern.chainsaw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories
public class ChainsawApplication {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    public static void main(final String[] args) {
        SpringApplication.run(ChainsawApplication.class, args);
    }

    /*@PostConstruct
    public void initLiquibase() {
        try (MongoLiquibaseDatabase database =
                (MongoLiquibaseDatabase) DatabaseFactory.getInstance().openDatabase(uri, null, null, null, null)) {
            Liquibase liquibase = new Liquibase("db-xml/master.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("");
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Bean
    public CommandLineRunner commandLineRunner(final UserService userService) {
        return args -> {
            userService.create(new User("asd", "asd"));
            userService.create(new User("jim", "jim@jim.com"));
            userService.create(new User("woah", "woah"));
        };
    }*/
}

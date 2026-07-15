package betr.intern.chainsaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories
public class ChainsawApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ChainsawApplication.class, args);
    }
}

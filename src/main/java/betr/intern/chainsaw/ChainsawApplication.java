package betr.intern.chainsaw;

import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChainsawApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ChainsawApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final UserService userService) {
        return args -> {
            userService.create(new User("asd", "asd"));
            userService.create(new User("jim", "jim@jim.com"));
            userService.create(new User("woah", "woah"));
        };
    }
}

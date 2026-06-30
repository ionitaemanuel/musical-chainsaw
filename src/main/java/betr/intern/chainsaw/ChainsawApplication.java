package betr.intern.chainsaw;

import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class ChainsawApplication {

    // app without security password will be on port 8082
    // change yaml for default config
    public static void main(String[] args) {
        SpringApplication.run(ChainsawApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            userService.create(new User("asd", "asd"));
            userService.create(new User("jim", "jim@jim.com"));
        };
    }

    @Bean
    @Profile("dev")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

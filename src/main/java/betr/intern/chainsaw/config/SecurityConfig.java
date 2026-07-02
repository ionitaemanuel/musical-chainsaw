package betr.intern.chainsaw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        final UserDetails user = User.builder()
                .username("john")
                .password("{noop}password")
                .roles(RoleEnum.USER.name())
                .build();
        final UserDetails user2 = User.builder()
                .username("woah")
                .password("{noop}password")
                .roles(RoleEnum.USER.name())
                .build();
        final UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admins")
                .roles(RoleEnum.ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(user, user2, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/users/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll());
        return http.build();
    }
}

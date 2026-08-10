package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers(
                                "/css/**",
                                "/images/**",
                                "/sign-in",
                                "/sign-up"
                        ).permitAll()

                        // Admin
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // Additional authentication
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }
}

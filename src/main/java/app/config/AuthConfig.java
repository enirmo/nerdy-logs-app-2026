package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Normal user
                        .requestMatchers(
                                "/css/**",
                                "/images/**",
                                "/sign-in",
                                "/sign-up"
                        ).permitAll()

                        // Admin
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // Authenticate other
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/sign-in")
                        .loginProcessingUrl("/sign-in")
                        .defaultSuccessUrl("/watchlist", true)
                        .failureUrl("/sign-in?error=true")
                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/sign-in")
                        .permitAll()
                );

        return http.build();
    }
}

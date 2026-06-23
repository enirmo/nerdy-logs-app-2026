package app.init;

import app.config.UserProperties;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.repository.user.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserInit implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UserProperties userProperties;
    private final PasswordEncoder passwordEncoder;

    public UserInit(
            UserRepository userRepository,
            UserProperties userProperties, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProperties = userProperties;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        UserProperties.DefaultUser admin = userProperties.getDefaultAdmin();

        boolean adminDoesNotExist = userRepository
                .findByUsername(admin.getUsername())
                .isEmpty();

        if (adminDoesNotExist) {
            User user = User.builder()
                    .username(admin.getUsername())
                    .email(admin.getEmail())
                    .password(passwordEncoder.encode(admin.getPassword()))
                    .profilePicture(admin.getProfilePicture())
                    .role(Role.ADMIN)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            userRepository.save(user);
        }
    }
}

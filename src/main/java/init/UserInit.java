package init;

import app.config.UserProperties;
import app.model.dto.user.UserRegisterRequest;
import app.repository.user.UserRepository;
import app.service.user.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserInit implements ApplicationRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserProperties userProperties;

    public UserInit(
            UserService userService,
            UserRepository userRepository,
            UserProperties userProperties) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userProperties = userProperties;
    }

    @Override
    public void run(ApplicationArguments args) {
        UserProperties.DefaultUser defaultUser = userProperties.getDefaultUser();

        boolean defaultUserDoesNotExist = userRepository
                .findByUsername(defaultUser.getUsername())
                .isEmpty();

        if (defaultUserDoesNotExist) {
            UserRegisterRequest request = UserRegisterRequest.builder()
                    .username(defaultUser.getUsername())
                    .email(defaultUser.getEmail())
                    .password(defaultUser.getPassword())
                    .profilePicture(userProperties.getDefaultUser().getProfilePicture())
                    .build();

            userService.register(request);
        }
    }
}
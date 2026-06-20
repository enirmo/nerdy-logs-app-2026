package app.service.user;
import app.config.UserProperties;
import app.model.dto.user.UserLoginRequest;
import app.model.dto.user.UserRegisterRequest;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProperties userProperties;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserProperties userProperties) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userProperties = userProperties;
    }

    public void register(UserRegisterRequest userRegisterRequest){
        // 1. Check if username/email exist
         userRepository.findByUsername(userRegisterRequest.getUsername())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Username is taken.");
                });

         userRepository.findByEmail(userRegisterRequest.getEmail())
                 .ifPresent(user -> {
                     throw new IllegalArgumentException("Email already registered.");
                 });

        // -- Extra: Setting current time for easy access
        LocalDateTime now = LocalDateTime.now();

        // -- Extra If new user, encode pass and register with chosen password
        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        // 2. Create the user
         User user = User.builder()
                 .username(userRegisterRequest.getUsername())
                 .password(encodedPassword)
                 .profilePicture(userProperties.getDefaultUser().getProfilePicture())
                 .email(userRegisterRequest.getEmail())
                 .role(Role.USER)
                 .createdOn(now)
                 .updatedOn(now)
                 .build();
         user = userRepository.save(user);

        // 3. Create empty library for new user
        //LibraryService.createDefaultLibrary(user);


        // To self: No point in bringing this to mapper, when register() has the job of creating
        // the User itself
    }

    public User login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByUsername(userLoginRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Username doesn't exist."));

        boolean passwordMatches = passwordEncoder.matches(
                        userLoginRequest.getPassword(),
                        user.getPassword()
        );

        if (!passwordMatches) {
            throw new IllegalArgumentException("Wrong password.");
        }

        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        return user;
    }
}

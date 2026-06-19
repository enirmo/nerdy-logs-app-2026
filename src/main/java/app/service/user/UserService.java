package app.service.user;
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

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(UserRegisterRequest userRegisterRequest) {
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
}

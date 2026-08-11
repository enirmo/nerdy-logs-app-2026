package app.service.user;
import app.config.UserProperties;
import app.model.dto.user.UserRegisterRequest;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.repository.library.LibraryRepository;
import app.repository.user.UserRepository;
import app.service.adminlog.AdminLogService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static app.messages.ErrorMessages.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProperties userProperties;
    private final AdminLogService adminLogService;
    private final LibraryRepository libraryRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserProperties userProperties, AdminLogService adminLogService, LibraryRepository libraryRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userProperties = userProperties;
        this.adminLogService = adminLogService;
        this.libraryRepository = libraryRepository;
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void register(UserRegisterRequest userRegisterRequest){
        // 1. Check if username/email exist
         userRepository.findByUsername(userRegisterRequest.getUsername())
                .ifPresent(user -> {
                    throw new IllegalArgumentException(USERNAME_TAKEN);
                });

         userRepository.findByEmail(userRegisterRequest.getEmail())
                 .ifPresent(user -> {
                     throw new IllegalArgumentException(EMAIL_ALREADY_REGISTERED);
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
         userRepository.save(user);
    }

    // Update profile - user side
    public void updateProfile(UUID userId, String profilePicture, String bio) {
        User user = getById(userId);

        user.setProfilePicture(profilePicture);
        user.setBio(bio);
        user.setUpdatedOn(LocalDateTime.now());

        userRepository.save(user);

    }

    // Delete user - this is for admins
    @Transactional
    public void deleteUser(UUID id) {
        User user = getById(id);

        libraryRepository.deleteAllByUser(user);

        userRepository.delete(user);

        adminLogService.logAction(
                String.format("Deleted user %s", user.getUsername())
        );
    }

    // Change user role
    public void changeRole(UUID userId, Role role) {
        User user = getById(userId);

        user.setRole(role);
        userRepository.save(user);
    }

    // Search users - for admins
    public List<User> searchUsers(String search) {
        if (search == null || search.isBlank()) {
            return getAllUsers();
        }

        return userRepository.findByUsernameContainingIgnoreCase(search);
    }

    // Find user by username
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
    }

}

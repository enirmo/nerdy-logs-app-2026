package app.user;

import app.config.UserProperties;
import app.model.dto.user.UserLoginRequest;
import app.model.dto.user.UserRegisterRequest;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.repository.library.LibraryRepository;
import app.repository.user.UserRepository;
import app.service.adminlog.AdminLogService;
import app.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static app.messages.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private UserProperties userProperties;
    @Mock
    private AdminLogService adminLogService;
    @Mock
    private LibraryRepository libraryRepository;
    @InjectMocks
    private UserService userService;


    // Get by ID tests
    @Test
    void getByIdReturnsUserWhenUserExists() {
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        User result = userService.getById(userId);

        assertEquals(user, result);

        verify(userRepository).findById(userId);
    }

    @Test
    void getByIdThrowsExceptionWhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getById(userId)
        );

        assertEquals(USER_NOT_FOUND, exception.getMessage());

        verify(userRepository).findById(userId);
    }


    // Get all users test
    @Test
    void getAllUsersReturnsAllUsers() {

        User user1 = User.builder()
                .username("user1")
                .build();

        User user2 = User.builder()
                .username("user2")
                .build();

        List<User> users = List.of(user1, user2);

        when(userRepository.findAll())
                .thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users, result);

        verify(userRepository).findAll();
    }


    // Get user by username tests
    @Test
    void getByUsernameReturnsUserWhenUsernameExists() {

        User user = User.builder()
                .username("user")
                .build();

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.of(user));

        User result = userService.getByUsername("user");

        assertEquals(user, result);

        verify(userRepository).findByUsername("user");
    }

    @Test
    void getByUsernameThrowsExceptionWhenUserDoesNotExist() {

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getByUsername("user")
        );

        assertEquals(USER_NOT_FOUND, exception.getMessage());

        verify(userRepository).findByUsername("user");
    }


    // Register tests
    @Test
    void registerCreatesUserWhenDataIsValid() {

        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("user")
                .email("user@email.com")
                .password("password")
                .build();

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.empty());

        when(userRepository.findByEmail("user@email.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("password"))
                .thenReturn("encodedPassword");

        when(userProperties.getDefaultUser().getProfilePicture())
                .thenReturn("/images/default-pfp.png");

        userService.register(request);

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals("user", savedUser.getUsername());
        assertEquals("user@email.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("/images/default-pfp.png", savedUser.getProfilePicture());
        assertEquals(Role.USER, savedUser.getRole());

        assertNotNull(savedUser.getCreatedOn());
        assertNotNull(savedUser.getUpdatedOn());

        verify(passwordEncoder).encode("password");
    }

    @Test
    void registerThrowsExceptionWhenUsernameIsTaken() {

        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("user")
                .email("user@email.com")
                .password("password")
                .build();

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.of(new User()));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register(request)
        );

        assertEquals(USERNAME_TAKEN, exception.getMessage());

        verify(userRepository).findByUsername("user");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerThrowsExceptionWhenEmailIsAlreadyRegistered() {

        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("user")
                .email("user@email.com")
                .password("password")
                .build();

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.empty());

        when(userRepository.findByEmail("user@email.com"))
                .thenReturn(Optional.of(new User()));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register(request)
        );

        assertEquals(EMAIL_ALREADY_REGISTERED, exception.getMessage());

        verify(userRepository).findByUsername("user");
        verify(userRepository).findByEmail("user@email.com");
        verify(userRepository, never()).save(any(User.class));
    }


    // Login tests
    @Test
    void loginWhenDetailsAreValid() {
        User user = User.builder()
                .username("user")
                .email("user@email.com")
                .password("password")
                .build();

        UserLoginRequest request = UserLoginRequest.builder()
                .username("user")
                .password("password")
                .build();

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "password"))
                .thenReturn(true);

        User result = userService.login(request);

        assertEquals(user, result);

        verify(userRepository).findByUsername("user");
        verify(passwordEncoder).matches("password", "password");
    }

    @Test
    void loginThrowsExceptionWhenUsernameDoesNotExist() {

        UserLoginRequest request = UserLoginRequest.builder()
                .username("user")
                .password("password")
                .build();

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.login(request)
        );

        assertEquals(USERNAME_DOES_NOT_EXIST, exception.getMessage());
    }

    @Test
    void loginThrowsExceptionWhenPasswordInvalid() {
        User user = User.builder()
                .username("user")
                .email("user@email.com")
                .password("password")
                .build();

        UserLoginRequest request = UserLoginRequest.builder()
                .username("user")
                .password("password123")
                .build();

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "password"))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.login(request)
        );

        assertEquals(WRONG_PASSWORD, exception.getMessage());
    }


    // Update profile test
    @Test
    void updateProfileChangesDetails() {
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .email("user@email.com")
                .password("password")
                .profilePicture("/images/default-pfp.png")
                .bio("something")
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        userService.updateProfile(userId,"/images/admin-pfp.jpg", "something123");

        assertEquals("something123", user.getBio());
        assertEquals("/images/admin-pfp.jpg", user.getProfilePicture());

        verify(userRepository).save(user);
    }


    // Delete user tests
    @Test
    void deleteUserAndLibraryAndLogsWhenUserExists() {
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(libraryRepository).deleteAllByUser(user);
        verify(userRepository).delete(user);
        verify(adminLogService).logAction(
                "Deleted user user"
        );
    }

    @Test
    void deleteUserThrowsExceptionWhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.deleteUser(userId));

        assertEquals(USER_NOT_FOUND, exception.getMessage());

        verify(userRepository, never()).delete(any(User.class));
        verify(libraryRepository, never()).deleteAllByUser(any(User.class));
        verify(adminLogService, never()).logAction(anyString());
    }


    // Change role test
    @Test
    void changeUserRole() {
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .role(Role.USER)
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        userService.changeRole(userId, Role.ADMIN);

        assertEquals(Role.ADMIN, user.getRole());
        verify(userRepository).save(user);
    }


    // Search users test
    @Test
    void searchUsersWhenBlankUsername() {
        User user1 = User.builder()
                .username("user1")
                .build();

        User user2 = User.builder()
                .username("user2")
                .build();

        List<User> allUsers = List.of(user1, user2);

        when(userRepository.findAll())
                .thenReturn(allUsers);

        List<User> resultUsers = userService.searchUsers("");

        assertEquals(allUsers, resultUsers);
    }

    @Test
    void searchUsersByUsernameReturnsMatchingResults() {
        User user = User.builder()
                .username("user")
                .build();

        List<User> allUsers = List.of(user);

        when(userRepository.findByUsernameContainingIgnoreCase("user"))
                .thenReturn(allUsers);

        List<User> searchedUsers = userService.searchUsers("user");

        assertEquals(allUsers, searchedUsers);
    }
}

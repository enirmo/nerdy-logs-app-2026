package app.service.user;

import app.messages.ErrorMessages;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.repository.user.UserRepository;
import app.service.user.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;


    // Load user details tests
    @Test
    void loadUserByUsernameReturnsUserDetailsWhenUserExists() {

        User user = User.builder()
                .username("user")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.of(user));

        UserDetails result =
                customUserDetailsService.loadUserByUsername("user");

        assertEquals("user", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(
                result.getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority().equals("ROLE_USER"))
        );
    }

    @Test
    void loadUserByUsernameThrowsExceptionWhenUserDoesNotExist() {

        when(userRepository.findByUsername("user"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("user")
        );

        assertEquals(
                ErrorMessages.USERNAME_DOES_NOT_EXIST,
                exception.getMessage()
        );
    }
}
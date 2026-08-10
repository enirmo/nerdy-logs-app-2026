package app.integration;

import app.model.dto.user.UserRegisterRequest;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.repository.user.UserRepository;
import app.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    // User registration integration tests
    @Test
    void registerSavesUserInDatabase() {

        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("integrationUser")
                .email("integration@test.com")
                .password("password123")
                .build();

        userService.register(request);

        User savedUser = userRepository
                .findByUsername("integrationUser")
                .orElseThrow();

        assertEquals("integrationUser", savedUser.getUsername());
        assertEquals("integration@test.com", savedUser.getEmail());
        assertEquals(Role.USER, savedUser.getRole());

        assertNotEquals("password123", savedUser.getPassword());
        assertNotNull(savedUser.getCreatedOn());
    }
}
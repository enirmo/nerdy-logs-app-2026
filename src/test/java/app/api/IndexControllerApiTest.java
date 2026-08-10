package app.api;

import app.service.user.UserService;
import app.web.IndexController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(IndexController.class)
class IndexControllerApiTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;

    // Sign up API tests
    @Test
    void getSignUpReturnsSignUpPage() throws Exception {

        mockMvc.perform(
                        get("/sign-up")
                                .with(user("user"))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("sign_in_pages/sign-up"))
                .andExpect(model().attributeExists("userRegisterRequest"));
    }
}
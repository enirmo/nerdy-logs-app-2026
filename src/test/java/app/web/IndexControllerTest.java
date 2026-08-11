package app.web;

import app.model.dto.user.UserRegisterRequest;
import app.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private IndexController indexController;


    // Index tests
    @Test
    void indexReturnsIndexPage() {

        String result = indexController.index();

        assertEquals("index", result);
    }

/*
    // Sign in tests
    @Test
    void getSignInPageReturnsSignInPage() {

        String result =
                indexController.getSignInPage(model);

        assertEquals("sign_in_pages/sign-in", result);

        verify(model).addAttribute(
                eq("userLoginRequest"),
                any()
        );
    }
*/

    // Sign up page tests
    @Test
    void getSignUpPageReturnsSignUpPage() {

        String result =
                indexController.getSignUpPage(model);

        assertEquals("sign_in_pages/sign-up", result);

        verify(model).addAttribute(
                eq("userRegisterRequest"),
                any()
        );
    }


    // Sign up tests
    @Test
    void setSignUpRegistersUserAndRedirectsWhenDataIsValid() {

        UserRegisterRequest request =
                mock(UserRegisterRequest.class);

        when(bindingResult.hasErrors())
                .thenReturn(false);

        String result =
                indexController.setSignUp(
                        request,
                        bindingResult,
                        model
                );

        assertEquals("redirect:/sign-in", result);

        verify(userService).register(request);
    }

    @Test
    void setSignUpReturnsSignUpPageWhenValidationFails() {

        UserRegisterRequest request =
                mock(UserRegisterRequest.class);

        when(bindingResult.hasErrors())
                .thenReturn(true);

        String result =
                indexController.setSignUp(
                        request,
                        bindingResult,
                        model
                );

        assertEquals("sign_in_pages/sign-up", result);

        verify(userService, never())
                .register(any(UserRegisterRequest.class));
    }

    @Test
    void setSignUpReturnsSignUpPageWhenRegistrationFails() {

        UserRegisterRequest request =
                mock(UserRegisterRequest.class);

        when(bindingResult.hasErrors())
                .thenReturn(false);

        doThrow(new IllegalArgumentException(
                "Username already exists"
        ))
                .when(userService)
                .register(request);

        String result =
                indexController.setSignUp(
                        request,
                        bindingResult,
                        model
                );

        assertEquals("sign_in_pages/sign-up", result);

        verify(model).addAttribute(
                "errorMessage",
                "Username already exists"
        );
    }


    // Access denied tests
    @Test
    void accessDeniedReturnsErrorPage() {

        String result =
                indexController.accessDenied(model);

        assertEquals("error", result);

        verify(model).addAttribute(
                "errorCode",
                403
        );

        verify(model).addAttribute(
                "errorMessage",
                "You shall not pass!"
        );
    }
}
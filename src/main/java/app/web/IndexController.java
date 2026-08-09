package app.web;

import app.messages.ErrorMessages;
import app.model.dto.user.UserLoginRequest;
import app.model.dto.user.UserRegisterRequest;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
            return "index";
        }

    // LOGIN MAP and POST
    @GetMapping("/sign-in")
    public String getSignInPage(Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "sign_in_pages/sign-in";
    }

    @PostMapping("/sign-in")
    public String setSignIn(
            @Valid @ModelAttribute("userLoginRequest") UserLoginRequest userLoginRequest,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "sign_in_pages/sign-in";
        }

        try {
            User user = userService.login(userLoginRequest);

            session.setAttribute("user_id", user.getId());
            session.setAttribute("role", user.getRole());

            if (user.getRole() == Role.ADMIN) {
                return "redirect:/admin";
            }

            return "redirect:/watchlist";

        } catch (IllegalArgumentException exception) {
            model.addAttribute(
                    "errorMessage",
                    ErrorMessages.INVALID_CREDENTIALS
            );

            return "sign_in_pages/sign-in";
        }
    }

    // REGISTER MAP and POST
    @GetMapping("/sign-up")
    public String getSignUpPage(Model model) {
        model.addAttribute("userRegisterRequest", new UserRegisterRequest());
        return "sign_in_pages/sign-up";
    }

    @PostMapping("/sign-up")
    public String setSignUp(
            @Valid @ModelAttribute("userRegisterRequest") UserRegisterRequest userRegisterRequest,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "sign_in_pages/sign-up";
        }

        try {
            userService.register(userRegisterRequest);

            return "redirect:/sign-in";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());

            return "sign_in_pages/sign-up";
        }
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/test-error")
    public String testError() {
        throw new RuntimeException("Testing global error handler");
    }
}

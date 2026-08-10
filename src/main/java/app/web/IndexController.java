package app.web;

import app.model.dto.user.UserLoginRequest;
import app.model.dto.user.UserRegisterRequest;
import app.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    /* @PostMapping("/sign-in")
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
    } */

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

    // Access denied
    @GetMapping("/access-denied")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied(Model model) {

        model.addAttribute("errorCode", 403);
        model.addAttribute(
                "errorMessage",
                "You shall not pass!"
        );

        return "error";
    }


    /* TEST ERRORS

    @GetMapping("/test-error")
    public String testError() {
        throw new RuntimeException("Testing global error handler");
    }

    @GetMapping("/test-bad-request/{id}")
    public String testBadRequest(@PathVariable UUID id) {
        return "redirect:/";
    }


    @GetMapping("/security-test")
    @ResponseBody
    public String securityTest(Authentication authentication) {
        return "Logged in as: " + authentication.getName();
    }
    */
}

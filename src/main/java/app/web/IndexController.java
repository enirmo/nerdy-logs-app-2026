package app.web;

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

    @GetMapping("/sign-in")
    public String getSignInPage() {
        return "sign_in_pages/sign-in";
    }

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
}

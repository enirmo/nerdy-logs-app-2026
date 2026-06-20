package app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
            return "index";
        }

    @GetMapping("/sign-in")
    public String getSignInPage() { return "sign_in_pages/sign-in"; }

    @GetMapping("/sign-up")
    public String getSignUpPage() {
            return "sign_in_pages/sign-up";
    }

    @GetMapping("/my-nerd-log")
    public String getLibraryPage() {
        return "watchlist";
    }
}

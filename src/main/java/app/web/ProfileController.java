package app.web;

import app.model.entity.user.User;
import app.service.item.ItemService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class ProfileController {

    private final UserService userService;
    private ItemService itemService;

    public ProfileController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/user")
    public String getUserPage(HttpSession session, Model model) {
        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            return "redirect:/sign-in";
        }

        User user = userService.getById(userId);

        model.addAttribute("user", user);

        return "profile/user";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        model.addAttribute("catalogItems", itemService.getAllItems());
        model.addAttribute("users", userService.getAllUsers());

        return "profile/admin";
    }

    @GetMapping("/admin/changelog")
    public String getHistoryPage(Model model) {

        //model.addAttribute("logs", adminLogService.getAllLogs());

        return "profile/admin-changelog";
    }
}
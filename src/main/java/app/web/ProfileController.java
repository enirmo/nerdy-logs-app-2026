package app.web;

import app.model.entity.user.User;
import app.service.adminlog.AdminLogService;
import app.service.item.ItemService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import app.model.dto.item.ItemRequest;
import app.model.entity.item.Genre;
import app.model.entity.item.Medium;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class ProfileController {

    private final UserService userService;
    private ItemService itemService;
    private AdminLogService adminLogService;

    public ProfileController(UserService userService, ItemService itemService,  AdminLogService adminLogService) {
        this.userService = userService;
        this.itemService = itemService;
        this.adminLogService = adminLogService;
    }

    private void loadAdminPageData(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("users", userService.getAllUsers());
    }

    @GetMapping("/profile")
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

    @GetMapping("/admin/items/add")
    public String getAddItemPage(Model model) {
        model.addAttribute("showAddForm", true);
        model.addAttribute("itemRequest", ItemRequest.builder().build());
        model.addAttribute("mediums", Medium.values());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("users", userService.getAllUsers());

        return "profile/admin";
    }

    @PostMapping("/admin/items/add")
    public String addItem(@ModelAttribute ItemRequest itemRequest) {
        itemService.createItem(itemRequest);
        adminLogService.logAction("Added item \"" + itemRequest.getItemName() + "\"");

        return "redirect:/admin";
    }
}
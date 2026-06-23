package app.web;

import app.model.entity.item.Item;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.service.adminlog.AdminLogService;
import app.service.item.ItemService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import app.model.dto.item.ItemRequest;
import app.model.entity.item.Genre;
import app.model.entity.item.Medium;

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

    // Check if user is admin
    private boolean isAdmin(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            return false;
        }

        User user = userService.getById(userId);

        return user.getRole() == Role.ADMIN;
    }

    // USER side

    // Show user profile
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


    // Edit user profile
    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam String profilePicture,
                              @RequestParam String bio,
                              HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            return "redirect:/sign-in";
        }

        userService.updateProfile(userId, profilePicture, bio);

        return "redirect:/profile";
    }


    // Delete user profile - user side
    @PostMapping("/profile/delete")
    public String deleteOwnProfile(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            return "redirect:/sign-in";
        }

        userService.deleteUser(userId);
        session.invalidate();

        return "redirect:/";
    }



    // ADMIN side

    // Get admin panel
    @GetMapping("/admin")
    public String getAdminPage(@RequestParam(required = false) String itemSearch,
                               @RequestParam(required = false) String userSearch,
                               Model model,
                               HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        model.addAttribute("items", itemService.searchItems(itemSearch));
        model.addAttribute("users", userService.searchUsers(userSearch));

        model.addAttribute("itemSearch", itemSearch);
        model.addAttribute("userSearch", userSearch);

        model.addAttribute("showAllItems", false);

        return "profile/admin";
    }

    // Get all items on admin panel
    @GetMapping("/admin/items/all")
    public String getAllAdminItems(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("showAllItems", true);

        return "profile/admin";
    }

    // Get admin action history
    @GetMapping("/admin/changelog")
    public String getHistoryPage(@RequestParam(required = false) String search, Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        model.addAttribute("logs", adminLogService.searchLogs(search));
        model.addAttribute("search", search);

        return "profile/admin-changelog";
    }

    // Show add item panel with all info to fill out
    @GetMapping("/admin/items/add")
    public String getAddItemPage(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        model.addAttribute("showAddForm", true);
        model.addAttribute("itemRequest", ItemRequest.builder().build());
        model.addAttribute("mediums", Medium.values());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("users", userService.getAllUsers());

        return "profile/admin";
    }


    // Add an item with the info
    @PostMapping("/admin/items/add")
    public String addItem(@Valid @ModelAttribute("itemRequest") ItemRequest itemRequest,
                          BindingResult bindingResult,
                          Model model,
                          HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("showAddForm", true);
            model.addAttribute("mediums", Medium.values());
            model.addAttribute("genres", Genre.values());
            model.addAttribute("items", itemService.searchItems(null));
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("itemSearch", null);
            model.addAttribute("userSearch", null);

            return "profile/admin";
        }

        itemService.createItem(itemRequest);
        adminLogService.logAction("Added item \"" + itemRequest.getItemName() + "\"");

        return "redirect:/admin";
    }

    // Get edit item panel
    @GetMapping("/admin/catalog/{id}/edit")
    public String getEditItemPage(@PathVariable UUID id, Model model,HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        Item item = itemService.getItem(id);

        ItemRequest itemRequest = ItemRequest.builder()
                .itemName(item.getName())
                .medium(item.getMediumType())
                .genre(item.getGenre())
                .description(item.getDescription())
                .pictureCover(item.getPictureCover())
                .releaseYear(item.getReleaseYear())
                .build();

        model.addAttribute("showAddForm", true);
        model.addAttribute("editMode", true);
        model.addAttribute("itemId", id);
        model.addAttribute("itemRequest", itemRequest);
        model.addAttribute("mediums", Medium.values());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("users", userService.getAllUsers());

        return "profile/admin";
    }

    // Edit items
    @PostMapping("/admin/catalog/{id}/edit")
    public String editItem(@PathVariable UUID id,
                           @ModelAttribute ItemRequest itemRequest,HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        itemService.updateItem(id, itemRequest);
        adminLogService.logAction("Updated item \"" + itemRequest.getItemName() + "\"");

        return "redirect:/admin";
    }

    // Delete items - admin side
    @PostMapping("/admin/items/{id}/delete")
    public String deleteItem(@PathVariable UUID id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        itemService.deleteItem(id);
        return "redirect:/admin";
    }

    // Delete user - admin side
    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable UUID id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/sign-in";
        }

        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
package app.web;

import app.model.entity.item.Item;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.service.adminlog.AdminLogService;
import app.service.item.ItemService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import app.model.dto.item.ItemRequest;
import app.model.entity.item.Genre;
import app.model.entity.item.Medium;

import java.util.UUID;

@Validated
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

    // USER side

    // Show user profile
    @GetMapping("/profile")
    public String getUserPage(Authentication authentication, Model model) {
        User user = userService.getByUsername(authentication.getName());

        model.addAttribute("user", user);

        return "profile/user";
    }


    // Edit user profile
    @PostMapping("/profile/edit")
    public String editProfile(
            @RequestParam
            @URL
            String profilePicture,

            @RequestParam
            @Size(max = 300) String bio,
            Authentication authentication) {

        User user = userService.getByUsername(authentication.getName());

        if (profilePicture.isBlank()) {
            profilePicture = user.getProfilePicture();
        }

        userService.updateProfile(
                user.getId(),
                profilePicture,
                bio
        );

        return "redirect:/profile";
    }


    // Delete user profile - user side
    @PostMapping("/profile/delete")
    public String deleteOwnProfile(
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response) {

        User user = userService.getByUsername(authentication.getName());

        userService.deleteUser(user.getId());

        new SecurityContextLogoutHandler()
                .logout(request, response, authentication);

        return "redirect:/sign-in";
    }



    // ADMIN side

    // Get admin panel
    @GetMapping("/admin")
    public String getAdminPage(@RequestParam(required = false) String itemSearch,
                               @RequestParam(required = false) String userSearch,
                               Model model) {

        model.addAttribute("items", itemService.searchItems(itemSearch));
        model.addAttribute("users", userService.searchUsers(userSearch));

        model.addAttribute("itemSearch", itemSearch);
        model.addAttribute("userSearch", userSearch);

        model.addAttribute("showAllItems", false);

        return "profile/admin";
    }

    // Get all items on admin panel
    @GetMapping("/admin/items/all")
    public String getAllAdminItems(Model model) {

        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("showAllItems", true);

        return "profile/admin";
    }

    // Get admin action history
    @GetMapping("/admin/changelog")
    public String getHistoryPage(@RequestParam(required = false) String search, Model model) {

        model.addAttribute("logs", adminLogService.searchLogs(search));
        model.addAttribute("search", search);

        return "profile/admin-changelog";
    }

    // Show add item panel with all info to fill out
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


    // Add an item with the info
    @PostMapping("/admin/items/add")
    public String addItem(@Valid @ModelAttribute("itemRequest") ItemRequest itemRequest,
                          BindingResult bindingResult,
                          Model model) {

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

        try {
            itemService.createItem(itemRequest);

        } catch (IllegalArgumentException exception) {

            model.addAttribute("errorMessage", exception.getMessage());

            model.addAttribute("showAddForm", true);
            model.addAttribute("mediums", Medium.values());
            model.addAttribute("genres", Genre.values());
            model.addAttribute("items", itemService.searchItems(null));
            model.addAttribute("users", userService.getAllUsers());

            return "profile/admin";
        }

        adminLogService.logAction("Added item \"" + itemRequest.getItemName() + "\"");

        return "redirect:/admin";
    }

    // Get edit item panel
    @GetMapping("/admin/catalog/{id}/edit")
    public String getEditItemPage(@PathVariable UUID id, Model model) {

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
    public String editItem(
            @PathVariable UUID id,
            @Valid @ModelAttribute("itemRequest") ItemRequest itemRequest,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("showAddForm", true);
            model.addAttribute("editMode", true);
            model.addAttribute("itemId", id);

            model.addAttribute("mediums", Medium.values());
            model.addAttribute("genres", Genre.values());
            model.addAttribute("items", itemService.getAllItems());
            model.addAttribute("users", userService.getAllUsers());

            return "profile/admin";
        }

        itemService.updateItem(id, itemRequest);

        adminLogService.logAction(
                "Updated item \"" + itemRequest.getItemName() + "\""
        );

        return "redirect:/admin";
    }

    // Delete items - admin side
    @PostMapping("/admin/items/{id}/delete")
    public String deleteItem(@PathVariable UUID id) {

        itemService.deleteItem(id);
        return "redirect:/admin";
    }

    // Delete user - admin side
    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);
        return "redirect:/admin";
    }

    // Change user role
    @PostMapping("/admin/users/{id}/role")
    public String changeUserRole(
            @PathVariable UUID id,
            @RequestParam Role role,
            Authentication authentication) {

        User currentAdmin = userService.getByUsername(authentication.getName());

        if (currentAdmin.getId().equals(id)) {
            return "redirect:/admin";
        }

        User user = userService.getById(id);
        Role previousRole = user.getRole();

        userService.changeRole(id, role);

        adminLogService.logAction(
                "Changed role for user \""
                        + user.getUsername()
                        + "\" from "
                        + previousRole
                        + " to "
                        + role
        );

        return "redirect:/admin";
    }
}
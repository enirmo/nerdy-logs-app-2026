package app.web;

import app.model.dto.item.ItemRequest;
import app.model.entity.item.Genre;
import app.model.entity.item.Item;
import app.model.entity.item.Medium;
import app.model.entity.user.Role;
import app.model.entity.user.User;
import app.service.adminlog.AdminLogService;
import app.service.item.ItemService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private ItemService itemService;
    @Mock
    private AdminLogService adminLogService;
    @Mock
    private Authentication authentication;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private ProfileController profileController;


    // User profile tests
    @Test
    void getUserPageReturnsProfilePage() {

        User user = User.builder()
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        String result =
                profileController.getUserPage(authentication, model);

        assertEquals("profile/user", result);

        verify(model).addAttribute("user", user);
    }

    @Test
    void editProfileUpdatesUserAndRedirects() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        String result =
                profileController.editProfile(
                        "/images/profile.jpg",
                        "New bio",
                        authentication
                );

        assertEquals("redirect:/profile", result);

        verify(userService).updateProfile(
                userId,
                "/images/profile.jpg",
                "New bio"
        );
    }

    @Test
    void deleteOwnProfileDeletesUserAndRedirectsToSignIn() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        String result =
                profileController.deleteOwnProfile(
                        authentication,
                        request,
                        response
                );

        assertEquals("redirect:/sign-in", result);

        verify(userService).deleteUser(userId);
    }


    // Admin page tests
    @Test
    void getAdminPageReturnsAdminPage() {

        List<Item> items = List.of(
                Item.builder()
                        .name("Storm of the Century")
                        .build()
        );

        List<User> users = List.of(
                User.builder()
                        .username("user")
                        .build()
        );

        when(itemService.searchItems("storm"))
                .thenReturn(items);

        when(userService.searchUsers("user"))
                .thenReturn(users);

        String result =
                profileController.getAdminPage(
                        "storm",
                        "user",
                        model
                );

        assertEquals("profile/admin", result);

        verify(model).addAttribute("items", items);
        verify(model).addAttribute("users", users);
        verify(model).addAttribute("itemSearch", "storm");
        verify(model).addAttribute("userSearch", "user");
        verify(model).addAttribute("showAllItems", false);
    }

    @Test
    void getAllAdminItemsReturnsAdminPageWithAllItems() {

        List<Item> items = List.of(
                Item.builder()
                        .name("Hollow Knight")
                        .build()
        );

        List<User> users = List.of(
                User.builder()
                        .username("user")
                        .build()
        );

        when(itemService.getAllItems())
                .thenReturn(items);

        when(userService.getAllUsers())
                .thenReturn(users);

        String result =
                profileController.getAllAdminItems(model);

        assertEquals("profile/admin", result);

        verify(model).addAttribute("items", items);
        verify(model).addAttribute("users", users);
        verify(model).addAttribute("showAllItems", true);
    }


    // Changelog tests
    @Test
    void getHistoryPageReturnsChangelogPage() {

        when(adminLogService.searchLogs("deleted"))
                .thenReturn(List.of());

        String result =
                profileController.getHistoryPage(
                        "deleted",
                        model
                );

        assertEquals("profile/admin-changelog", result);

        verify(adminLogService).searchLogs("deleted");
        verify(model).addAttribute("search", "deleted");
    }


    // Add item page tests
    @Test
    void getAddItemPageReturnsAdminPageWithForm() {

        String result =
                profileController.getAddItemPage(model);

        assertEquals("profile/admin", result);

        verify(model).addAttribute("showAddForm", true);
        verify(model).addAttribute(
                eq("mediums"),
                any()
        );
        verify(model).addAttribute(
                eq("genres"),
                any()
        );
    }


    // Add item tests
    @Test
    void addItemCreatesItemAndRedirectsWhenDataIsValid() {

        ItemRequest request = ItemRequest.builder()
                .itemName("Storm of the Century")
                .medium(Medium.BOOK)
                .genre(Genre.HORROR)
                .releaseYear(1999)
                .build();

        when(bindingResult.hasErrors())
                .thenReturn(false);

        String result =
                profileController.addItem(
                        request,
                        bindingResult,
                        model
                );

        assertEquals("redirect:/admin", result);

        verify(itemService).createItem(request);

        verify(adminLogService).logAction(
                "Added item \"Storm of the Century\""
        );
    }

    @Test
    void addItemReturnsAdminPageWhenValidationFails() {

        ItemRequest request = ItemRequest.builder()
                .itemName("")
                .build();

        when(bindingResult.hasErrors())
                .thenReturn(true);

        String result =
                profileController.addItem(
                        request,
                        bindingResult,
                        model
                );

        assertEquals("profile/admin", result);

        verify(itemService, never())
                .createItem(any(ItemRequest.class));

        verify(model).addAttribute("showAddForm", true);
    }

    @Test
    void addItemReturnsAdminPageWhenItemAlreadyExists() {

        ItemRequest request = ItemRequest.builder()
                .itemName("Harry Potter")
                .medium(Medium.MOVIE)
                .genre(Genre.FANTASY)
                .build();

        when(bindingResult.hasErrors())
                .thenReturn(false);

        doThrow(new IllegalArgumentException("Already exists"))
                .when(itemService)
                .createItem(request);

        String result =
                profileController.addItem(
                        request,
                        bindingResult,
                        model
                );

        assertEquals("profile/admin", result);

        verify(model).addAttribute(
                "errorMessage",
                "Already exists"
        );

        verify(adminLogService, never())
                .logAction(anyString());
    }


    // Edit item page tests
    @Test
    void getEditItemPageReturnsAdminPageWithExistingItem() {

        UUID itemId = UUID.randomUUID();

        Item item = Item.builder()
                .id(itemId)
                .name("Frieren")
                .mediumType(Medium.ANIME)
                .genre(Genre.FANTASY)
                .description("Description")
                .pictureCover("frieren.jpg")
                .releaseYear(2023)
                .build();

        when(itemService.getItem(itemId))
                .thenReturn(item);

        String result =
                profileController.getEditItemPage(
                        itemId,
                        model
                );

        assertEquals("profile/admin", result);

        verify(model).addAttribute("editMode", true);
        verify(model).addAttribute("itemId", itemId);
    }


    // Edit item tests
    @Test
    void editItemUpdatesItemAndRedirectsWhenDataIsValid() {

        UUID itemId = UUID.randomUUID();

        ItemRequest request = ItemRequest.builder()
                .itemName("The Rookie")
                .medium(Medium.SERIES)
                .genre(Genre.CRIME)
                .releaseYear(2018)
                .build();

        when(bindingResult.hasErrors())
                .thenReturn(false);

        String result =
                profileController.editItem(
                        itemId,
                        request,
                        bindingResult,
                        model
                );

        assertEquals("redirect:/admin", result);

        verify(itemService).updateItem(itemId, request);

        verify(adminLogService).logAction(
                "Updated item \"The Rookie\""
        );
    }

    @Test
    void editItemReturnsAdminPageWhenValidationFails() {

        UUID itemId = UUID.randomUUID();

        ItemRequest request = ItemRequest.builder()
                .itemName("")
                .build();

        when(bindingResult.hasErrors())
                .thenReturn(true);

        String result =
                profileController.editItem(
                        itemId,
                        request,
                        bindingResult,
                        model
                );

        assertEquals("profile/admin", result);

        verify(itemService, never())
                .updateItem(any(), any());

        verify(model).addAttribute("editMode", true);
    }


    // Delete item tests
    @Test
    void deleteItemDeletesItemAndRedirects() {

        UUID itemId = UUID.randomUUID();

        String result =
                profileController.deleteItem(itemId);

        assertEquals("redirect:/admin", result);

        verify(itemService).deleteItem(itemId);
    }


    // Delete user tests
    @Test
    void deleteUserDeletesUserAndRedirects() {

        UUID userId = UUID.randomUUID();

        String result =
                profileController.deleteUser(userId);

        assertEquals("redirect:/admin", result);

        verify(userService).deleteUser(userId);
    }


    // Change user role tests
    @Test
    void changeUserRoleChangesRoleAndLogsWhenChangingAnotherUser() {

        UUID adminId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        User admin = User.builder()
                .id(adminId)
                .username("admin")
                .role(Role.ADMIN)
                .build();

        User user = User.builder()
                .id(userId)
                .username("user")
                .role(Role.USER)
                .build();

        when(authentication.getName())
                .thenReturn("admin");

        when(userService.getByUsername("admin"))
                .thenReturn(admin);

        when(userService.getById(userId))
                .thenReturn(user);

        String result =
                profileController.changeUserRole(
                        userId,
                        Role.ADMIN,
                        authentication
                );

        assertEquals("redirect:/admin", result);

        verify(userService).changeRole(
                userId,
                Role.ADMIN
        );

        verify(adminLogService).logAction(
                "Changed role for user \"user\" from USER to ADMIN"
        );
    }

    @Test
    void changeUserRoleDoesNothingWhenAdminTargetsSelf() {

        UUID adminId = UUID.randomUUID();

        User admin = User.builder()
                .id(adminId)
                .username("admin")
                .role(Role.ADMIN)
                .build();

        when(authentication.getName())
                .thenReturn("admin");

        when(userService.getByUsername("admin"))
                .thenReturn(admin);

        String result =
                profileController.changeUserRole(
                        adminId,
                        Role.USER,
                        authentication
                );

        assertEquals("redirect:/admin", result);

        verify(userService, never())
                .changeRole(any(), any());

        verify(adminLogService, never())
                .logAction(anyString());
    }
}
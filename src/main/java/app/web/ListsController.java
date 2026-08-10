package app.web;

import app.model.dto.reviews.ReviewResponse;
import app.model.entity.item.Medium;
import app.model.entity.libraryentry.EntryStatus;
import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.User;
import app.service.library.LibraryService;
import app.service.reviews.ReviewIntegrationService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ListsController {
    private final UserService userService;
    private final LibraryService libraryService;
    private final ReviewIntegrationService reviewIntegrationService;

    public ListsController(UserService userService, LibraryService libraryService, ReviewIntegrationService reviewIntegrationService) {
        this.userService = userService;
        this.libraryService = libraryService;
        this.reviewIntegrationService = reviewIntegrationService;
    }


    // Helpers
    private List<LibraryEntry> filterByMedium(List<LibraryEntry> entries, Medium medium) {
        return entries.stream()
                .filter(entry -> entry.getItem().getMediumType() == medium)
                .toList();
    }

    private String loadLibraryPage(
            EntryStatus status,
            String activePage,
            String search, Model model,
            Authentication authentication) {

        User user = userService.getByUsername(authentication.getName());
        UUID userId = user.getId();

        List<LibraryEntry> entries =
                libraryService.searchEntriesByStatus(userId, status, search);

        Map<UUID, ReviewResponse> reviewsByMedia = new HashMap<>();
        Map<UUID, Double> averagesByMedia = new HashMap<>();

        for (LibraryEntry entry : entries) {

            UUID mediaId = entry.getItem().getId();

            ReviewResponse review =
                    reviewIntegrationService.getReview(userId, mediaId);

            if (review != null) {
                reviewsByMedia.put(mediaId, review);
            }

            double average =
                    reviewIntegrationService.getAverageReviews(mediaId);

            averagesByMedia.put(mediaId, average);
        }

        model.addAttribute("user", user);
        model.addAttribute("activePage", activePage);

        model.addAttribute("movies",
                filterByMedium(entries, Medium.MOVIE));

        model.addAttribute("series",
                filterByMedium(entries, Medium.SERIES));

        model.addAttribute("games",
                filterByMedium(entries, Medium.GAME));

        model.addAttribute("anime",
                filterByMedium(entries, Medium.ANIME));

        model.addAttribute("books",
                filterByMedium(entries, Medium.BOOK));

        model.addAttribute("currentPath", "/" + activePage);
        model.addAttribute("search", search);

        model.addAttribute("reviewsByMedia", reviewsByMedia);
        model.addAttribute("averagesByMedia", averagesByMedia);

        return "my_nerd_space/lists";
    }


    // All lists GET
    @GetMapping("/watchlist")
    public String watchlist(@RequestParam(required = false) String search, Model model, Authentication authentication) {
        return loadLibraryPage(
                EntryStatus.PLANNED,
                "watchlist",
                search,
                model,
                authentication
        );
    }

    @GetMapping("/completed")
    public String completed(@RequestParam(required = false) String search, Model model, Authentication authentication) {
        return loadLibraryPage(
                EntryStatus.COMPLETED,
                "completed",
                search,
                model,
                authentication
        );
    }

    @GetMapping("/in-progress")
    public String inProgress(@RequestParam(required = false) String search, Model model, Authentication authentication) {
        return loadLibraryPage(
                EntryStatus.STARTED,
                "in-progress",
                search,
                model,
                authentication
        );
    }

    @GetMapping("/dropped")
    public String dropped(@RequestParam(required = false) String search, Model model, Authentication authentication) {
        return loadLibraryPage(
                EntryStatus.DROPPED,
                "dropped",
                search, model,
                authentication
        );
    }


    // ADD top library
    @PostMapping("/library/add")
    public String addToLibrary(
            @RequestParam UUID itemId,
            @RequestParam EntryStatus entryStatus,
            @RequestParam String redirectTo,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        User user = userService.getByUsername(authentication.getName());
        UUID userId = user.getId();

        if (redirectTo == null || redirectTo.isBlank()) {
            redirectTo = "/";
        }

        try {
            libraryService.addItemToLibrary(userId, itemId, entryStatus);

            redirectAttributes.addFlashAttribute(
                    "addedItemId",
                    itemId
            );

        } catch (IllegalArgumentException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorItemId",
                    itemId
            );

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    exception.getMessage()
            );
        }

        return "redirect:" + redirectTo;
    }

    // REMOVE from library
    @PostMapping("/library/remove")
    public String removeFromLibrary(
            @RequestParam UUID entryId,
            @RequestParam String redirectTo,
            RedirectAttributes redirectAttributes) {

        try {
            libraryService.removeFromLibrary(entryId);

        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    exception.getMessage()
            );
        }

        return "redirect:" + redirectTo;
    }


}


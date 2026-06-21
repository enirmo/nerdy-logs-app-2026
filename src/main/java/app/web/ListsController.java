package app.web;

import app.model.entity.item.Medium;
import app.model.entity.libraryentry.EntryStatus;
import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.User;
import app.service.library.LibraryService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class ListsController {
    private UserService userService;
    private LibraryService libraryService;

    public ListsController(UserService userService,  LibraryService libraryService) {
        this.userService = userService;
        this.libraryService = libraryService;
    }

    private List<LibraryEntry> filterByMedium(List<LibraryEntry> entries, Medium medium) {
        return entries.stream()
                .filter(entry -> entry.getItem().getMediumType() == medium)
                .toList();
    }

    private String loadLibraryPage(
            EntryStatus status,
            String activePage,
            Model model,
            HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            return "redirect:/sign-in";
        }

        User user = userService.getById(userId);

        List<LibraryEntry> entries =
                libraryService.getEntriesByStatus(userId, status);

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

        return "my_nerd_space/lists";
    }

    @GetMapping("/watchlist")
    public String watchlist(Model model, HttpSession session) {
        return loadLibraryPage(
                EntryStatus.PLANNED,
                "watchlist",
                model,
                session);
    }

    @GetMapping("/completed")
    public String completed(Model model, HttpSession session) {
        return loadLibraryPage(
                EntryStatus.COMPLETED,
                "completed",
                model,
                session);
    }

    @GetMapping("/in-progress")
    public String inProgress(Model model, HttpSession session) {
        return loadLibraryPage(
                EntryStatus.STARTED,
                "in-progress",
                model,
                session);
    }

    @GetMapping("/dropped")
    public String dropped(Model model, HttpSession session) {
        return loadLibraryPage(
                EntryStatus.DROPPED,
                "dropped",
                model,
                session);
    }
}


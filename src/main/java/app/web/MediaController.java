package app.web;

import app.model.entity.item.Medium;
import app.service.item.ItemService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class MediaController {

    private final ItemService itemService;
    private final UserService userService;

    public MediaController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    private void addLoggedUser(Model model, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId != null) {
            model.addAttribute("user", userService.getById(userId));
        }
    }

    // ADD ITEM for each category

    @GetMapping("/movies")
    public String movies(@RequestParam(required = false) String search, Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Movies");
        model.addAttribute("items", itemService.searchByMedium(Medium.MOVIE, search));
        model.addAttribute("currentPath", "/movies");
        model.addAttribute("search", search);

        addLoggedUser(model, session);

        return "media_tabs/medium";
    }

    @GetMapping("/series")
    public String series(@RequestParam(required = false) String search, Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Series");
        model.addAttribute("items", itemService.searchByMedium(Medium.SERIES, search));
        model.addAttribute("currentPath", "/series");
        model.addAttribute("search", search);

        addLoggedUser(model, session);

        return "media_tabs/medium";
    }

    @GetMapping("/games")
    public String games(@RequestParam(required = false) String search, Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Games");
        model.addAttribute("items", itemService.searchByMedium(Medium.GAME, search));
        model.addAttribute("currentPath", "/games");
        model.addAttribute("search", search);

        addLoggedUser(model, session);

        return "media_tabs/medium";
    }

    @GetMapping("/anime")
    public String anime(@RequestParam(required = false) String search, Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Anime");
        model.addAttribute("items", itemService.searchByMedium(Medium.ANIME, search));
        model.addAttribute("currentPath", "/anime");
        model.addAttribute("search", search);

        addLoggedUser(model, session);

        return "media_tabs/medium";
    }

    @GetMapping("/books")
    public String books(@RequestParam(required = false) String search, Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Books");
        model.addAttribute("items", itemService.searchByMedium(Medium.BOOK, search));
        model.addAttribute("currentPath", "/books");
        model.addAttribute("search", search);

        addLoggedUser(model, session);

        return "media_tabs/medium";
    }

}

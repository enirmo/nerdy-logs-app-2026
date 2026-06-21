package app.web;

import app.model.entity.item.Medium;
import app.service.item.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MediaController {

    private final ItemService itemService;

    public MediaController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/movies")
    public String movies(Model model) {
        model.addAttribute("pageTitle", "Movies");
        model.addAttribute("items", itemService.getItemsByMediumType(Medium.MOVIE));
        return "media_tabs/medium";
    }

    @GetMapping("/series")
    public String series(Model model) {
        model.addAttribute("pageTitle", "Series");
        model.addAttribute("items", itemService.getItemsByMediumType(Medium.SERIES));
        return "media_tabs/medium";
    }

    @GetMapping("/games")
    public String games(Model model) {
        model.addAttribute("pageTitle", "Games");
        model.addAttribute("items", itemService.getItemsByMediumType(Medium.GAME));
        return "media_tabs/medium";
    }

    @GetMapping("/anime")
    public String anime(Model model) {
        model.addAttribute("pageTitle", "Anime");
        model.addAttribute("items", itemService.getItemsByMediumType(Medium.ANIME));
        return "media_tabs/medium";
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("pageTitle", "Books");
        model.addAttribute("items", itemService.getItemsByMediumType(Medium.BOOK));
        return "media_tabs/medium";
    }
}

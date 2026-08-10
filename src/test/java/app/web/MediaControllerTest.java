package app.web;

import app.model.entity.item.Item;
import app.model.entity.item.Medium;
import app.model.entity.user.User;
import app.service.item.ItemService;
import app.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MediaControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private MediaController mediaController;


    // Movies tests
    @Test
    void moviesReturnsMoviesPage() {

        User user = User.builder()
                .username("user")
                .build();

        Item movie = Item.builder()
                .name("Harry Potter")
                .mediumType(Medium.MOVIE)
                .build();

        List<Item> movies = List.of(movie);

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(itemService.searchByMedium(
                Medium.MOVIE,
                "Harry Potter"
        )).thenReturn(movies);

        String result =
                mediaController.movies(
                        "Harry Potter",
                        model,
                        authentication
                );

        assertEquals("media_tabs/medium", result);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("pageTitle", "Movies");
        verify(model).addAttribute("items", movies);
        verify(model).addAttribute("currentPath", "/movies");
        verify(model).addAttribute("search", "Harry Potter");
    }


    // Series tests
    @Test
    void seriesReturnsSeriesPage() {

        User user = User.builder()
                .username("user")
                .build();

        Item series = Item.builder()
                .name("The Rookie")
                .mediumType(Medium.SERIES)
                .build();

        List<Item> seriesItems = List.of(series);

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(itemService.searchByMedium(
                Medium.SERIES,
                "The Rookie"
        )).thenReturn(seriesItems);

        String result =
                mediaController.series(
                        "The Rookie",
                        model,
                        authentication
                );

        assertEquals("media_tabs/medium", result);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("pageTitle", "Series");
        verify(model).addAttribute("items", seriesItems);
        verify(model).addAttribute("currentPath", "/series");
        verify(model).addAttribute("search", "The Rookie");
    }


    // Games tests
    @Test
    void gamesReturnsGamesPage() {

        User user = User.builder()
                .username("user")
                .build();

        Item game = Item.builder()
                .name("Hollow Knight")
                .mediumType(Medium.GAME)
                .build();

        List<Item> games = List.of(game);

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(itemService.searchByMedium(
                Medium.GAME,
                "Hollow Knight"
        )).thenReturn(games);

        String result =
                mediaController.games(
                        "Hollow Knight",
                        model,
                        authentication
                );

        assertEquals("media_tabs/medium", result);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("pageTitle", "Games");
        verify(model).addAttribute("items", games);
        verify(model).addAttribute("currentPath", "/games");
        verify(model).addAttribute("search", "Hollow Knight");
    }


    // Anime tests
    @Test
    void animeReturnsAnimePage() {

        User user = User.builder()
                .username("user")
                .build();

        Item anime = Item.builder()
                .name("Frieren")
                .mediumType(Medium.ANIME)
                .build();

        List<Item> animeItems = List.of(anime);

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(itemService.searchByMedium(
                Medium.ANIME,
                "Frieren"
        )).thenReturn(animeItems);

        String result =
                mediaController.anime(
                        "Frieren",
                        model,
                        authentication
                );

        assertEquals("media_tabs/medium", result);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("pageTitle", "Anime");
        verify(model).addAttribute("items", animeItems);
        verify(model).addAttribute("currentPath", "/anime");
        verify(model).addAttribute("search", "Frieren");
    }


    // Books tests
    @Test
    void booksReturnsBooksPage() {

        User user = User.builder()
                .username("user")
                .build();

        Item book = Item.builder()
                .name("Storm of the Century")
                .mediumType(Medium.BOOK)
                .build();

        List<Item> books = List.of(book);

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(itemService.searchByMedium(
                Medium.BOOK,
                "Storm of the Century"
        )).thenReturn(books);

        String result =
                mediaController.books(
                        "Storm of the Century",
                        model,
                        authentication
                );

        assertEquals("media_tabs/medium", result);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("pageTitle", "Books");
        verify(model).addAttribute("items", books);
        verify(model).addAttribute("currentPath", "/books");
        verify(model).addAttribute(
                "search",
                "Storm of the Century"
        );
    }
}
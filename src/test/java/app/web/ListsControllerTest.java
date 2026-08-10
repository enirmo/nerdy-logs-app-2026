package app.web;

import app.model.dto.reviews.ReviewResponse;
import app.model.entity.item.Item;
import app.model.entity.item.Medium;
import app.model.entity.libraryentry.EntryStatus;
import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.User;
import app.service.library.LibraryService;
import app.service.reviews.ReviewIntegrationService;
import app.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListsControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private LibraryService libraryService;

    @Mock
    private ReviewIntegrationService reviewIntegrationService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ListsController listsController;


    // Watchlist tests
    @Test
    void watchlistReturnsLibraryPageWithFilteredEntries() {

        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        Item book = Item.builder()
                .id(bookId)
                .name("Storm of the Century")
                .mediumType(Medium.BOOK)
                .build();

        Item game = Item.builder()
                .id(gameId)
                .name("Hollow Knight")
                .mediumType(Medium.GAME)
                .build();

        LibraryEntry bookEntry = new LibraryEntry();
        bookEntry.setUser(user);
        bookEntry.setItem(book);
        bookEntry.setEntryStatus(EntryStatus.PLANNED);

        LibraryEntry gameEntry = new LibraryEntry();
        gameEntry.setUser(user);
        gameEntry.setItem(game);
        gameEntry.setEntryStatus(EntryStatus.PLANNED);

        List<LibraryEntry> entries =
                List.of(bookEntry, gameEntry);

        ReviewResponse review = mock(ReviewResponse.class);

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(libraryService.searchEntriesByStatus(
                userId,
                EntryStatus.PLANNED,
                ""
        )).thenReturn(entries);

        when(reviewIntegrationService.getReview(userId, bookId))
                .thenReturn(review);

        when(reviewIntegrationService.getReview(userId, gameId))
                .thenReturn(null);

        when(reviewIntegrationService.getAverageReviews(bookId))
                .thenReturn(8.5);

        when(reviewIntegrationService.getAverageReviews(gameId))
                .thenReturn(9.0);

        String result =
                listsController.watchlist(
                        "",
                        model,
                        authentication
                );

        assertEquals("my_nerd_space/lists", result);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("activePage", "watchlist");

        verify(model).addAttribute(
                "books",
                List.of(bookEntry)
        );

        verify(model).addAttribute(
                "games",
                List.of(gameEntry)
        );

        verify(model).addAttribute(
                "movies",
                List.of()
        );

        verify(model).addAttribute(
                "series",
                List.of()
        );

        verify(model).addAttribute(
                "anime",
                List.of()
        );

        verify(model).addAttribute(
                "currentPath",
                "/watchlist"
        );

        verify(model).addAttribute("search", "");
    }


    // Completed tests
    @Test
    void completedLoadsCompletedEntries() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(libraryService.searchEntriesByStatus(
                userId,
                EntryStatus.COMPLETED,
                null
        )).thenReturn(List.of());

        String result =
                listsController.completed(
                        null,
                        model,
                        authentication
                );

        assertEquals("my_nerd_space/lists", result);

        verify(libraryService).searchEntriesByStatus(
                userId,
                EntryStatus.COMPLETED,
                null
        );

        verify(model).addAttribute(
                "activePage",
                "completed"
        );

        verify(model).addAttribute(
                "currentPath",
                "/completed"
        );
    }


    // In progress tests
    @Test
    void inProgressLoadsStartedEntries() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(libraryService.searchEntriesByStatus(
                userId,
                EntryStatus.STARTED,
                null
        )).thenReturn(List.of());

        String result =
                listsController.inProgress(
                        null,
                        model,
                        authentication
                );

        assertEquals("my_nerd_space/lists", result);

        verify(libraryService).searchEntriesByStatus(
                userId,
                EntryStatus.STARTED,
                null
        );

        verify(model).addAttribute(
                "activePage",
                "in-progress"
        );

        verify(model).addAttribute(
                "currentPath",
                "/in-progress"
        );
    }


    // Dropped tests
    @Test
    void droppedLoadsDroppedEntries() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        when(libraryService.searchEntriesByStatus(
                userId,
                EntryStatus.DROPPED,
                null
        )).thenReturn(List.of());

        String result =
                listsController.dropped(
                        null,
                        model,
                        authentication
                );

        assertEquals("my_nerd_space/lists", result);

        verify(libraryService).searchEntriesByStatus(
                userId,
                EntryStatus.DROPPED,
                null
        );

        verify(model).addAttribute(
                "activePage",
                "dropped"
        );

        verify(model).addAttribute(
                "currentPath",
                "/dropped"
        );
    }


    // Add to library tests
    @Test
    void addToLibraryAddsItemAndRedirectsWhenDataIsValid() {

        UUID userId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        String result =
                listsController.addToLibrary(
                        itemId,
                        EntryStatus.PLANNED,
                        "/books",
                        authentication,
                        redirectAttributes
                );

        assertEquals("redirect:/books", result);

        verify(libraryService).addItemToLibrary(
                userId,
                itemId,
                EntryStatus.PLANNED
        );

        verify(redirectAttributes).addFlashAttribute(
                "addedItemId",
                itemId
        );
    }

    @Test
    void addToLibraryRedirectsHomeWhenRedirectIsBlank() {

        UUID userId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        String result =
                listsController.addToLibrary(
                        itemId,
                        EntryStatus.PLANNED,
                        "",
                        authentication,
                        redirectAttributes
                );

        assertEquals("redirect:/", result);

        verify(libraryService).addItemToLibrary(
                userId,
                itemId,
                EntryStatus.PLANNED
        );
    }

    @Test
    void addToLibraryShowsErrorWhenAddingFails() {

        UUID userId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        doThrow(new IllegalArgumentException(
                "Already in library"
        ))
                .when(libraryService)
                .addItemToLibrary(
                        userId,
                        itemId,
                        EntryStatus.PLANNED
                );

        String result =
                listsController.addToLibrary(
                        itemId,
                        EntryStatus.PLANNED,
                        "/books",
                        authentication,
                        redirectAttributes
                );

        assertEquals("redirect:/books", result);

        verify(redirectAttributes).addFlashAttribute(
                "errorItemId",
                itemId
        );

        verify(redirectAttributes).addFlashAttribute(
                "errorMessage",
                "Already in library"
        );

        verify(
                redirectAttributes,
                never()
        ).addFlashAttribute(
                "addedItemId",
                itemId
        );
    }


    // Remove from library tests
    @Test
    void removeFromLibraryRemovesEntryAndRedirects() {

        UUID entryId = UUID.randomUUID();

        String result =
                listsController.removeFromLibrary(
                        entryId,
                        "/watchlist",
                        redirectAttributes
                );

        assertEquals("redirect:/watchlist", result);

        verify(libraryService).removeFromLibrary(entryId);
    }

    @Test
    void removeFromLibraryShowsErrorWhenRemovalFails() {

        UUID entryId = UUID.randomUUID();

        doThrow(new IllegalArgumentException(
                "Entry not found"
        ))
                .when(libraryService)
                .removeFromLibrary(entryId);

        String result =
                listsController.removeFromLibrary(
                        entryId,
                        "/watchlist",
                        redirectAttributes
                );

        assertEquals("redirect:/watchlist", result);

        verify(redirectAttributes).addFlashAttribute(
                "errorMessage",
                "Entry not found"
        );
    }
}
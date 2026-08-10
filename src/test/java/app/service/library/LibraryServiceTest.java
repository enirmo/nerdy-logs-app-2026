package app.service.library;

import app.exceptions.ItemNotFoundException;
import app.exceptions.ResourceAlreadyExistsException;
import app.model.entity.item.Item;
import app.model.entity.libraryentry.EntryStatus;
import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.User;
import app.repository.item.ItemRepository;
import app.repository.library.LibraryRepository;
import app.repository.user.UserRepository;
import app.service.library.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static app.messages.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LibraryService libraryService;


    // Add item tests
    @Test
    void addItemToLibraryAddsEntryWhenDataIsValid() {

        UUID userId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        Item item = Item.builder()
                .id(itemId)
                .name("Storm of the Century")
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.of(item));

        when(libraryRepository.findByUserAndItem(user, item))
                .thenReturn(Optional.empty());

        libraryService.addItemToLibrary(
                userId,
                itemId,
                EntryStatus.PLANNED
        );

        ArgumentCaptor<LibraryEntry> entryCaptor =
                ArgumentCaptor.forClass(LibraryEntry.class);

        verify(libraryRepository).save(entryCaptor.capture());

        LibraryEntry savedEntry = entryCaptor.getValue();

        assertEquals(user, savedEntry.getUser());
        assertEquals(item, savedEntry.getItem());
        assertEquals(EntryStatus.PLANNED, savedEntry.getEntryStatus());
    }

    @Test
    void addItemToLibraryThrowsExceptionWhenUserDoesNotExist() {

        UUID userId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> libraryService.addItemToLibrary(
                        userId,
                        itemId,
                        EntryStatus.PLANNED
                )
        );

        assertEquals(USER_NOT_FOUND, exception.getMessage());

        verify(libraryRepository, never())
                .save(any(LibraryEntry.class));
    }

    @Test
    void addItemToLibraryThrowsExceptionWhenItemDoesNotExist() {

        UUID userId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(
                ItemNotFoundException.class,
                () -> libraryService.addItemToLibrary(
                        userId,
                        itemId,
                        EntryStatus.PLANNED
                )
        );

        assertEquals(ITEM_NOT_FOUND, exception.getMessage());

        verify(libraryRepository, never())
                .save(any(LibraryEntry.class));
    }

    @Test
    void addItemToLibraryThrowsExceptionWhenItemAlreadyExists() {

        UUID userId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        Item item = Item.builder()
                .id(itemId)
                .name("Hollow Knight")
                .build();

        LibraryEntry existingEntry = new LibraryEntry();
        existingEntry.setUser(user);
        existingEntry.setItem(item);
        existingEntry.setEntryStatus(EntryStatus.PLANNED);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.of(item));

        when(libraryRepository.findByUserAndItem(user, item))
                .thenReturn(Optional.of(existingEntry));

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> libraryService.addItemToLibrary(
                        userId,
                        itemId,
                        EntryStatus.PLANNED
                )
        );

        assertEquals(
                ITEM_ALREADY_EXISTS_IN_LIBRARY,
                exception.getMessage()
        );

        verify(libraryRepository, never())
                .save(any(LibraryEntry.class));
    }


    // Remove entry tests
    @Test
    void removeFromLibraryDeletesEntryWhenEntryExists() {

        UUID entryId = UUID.randomUUID();

        Item item = Item.builder()
                .name("Harry Potter")
                .build();

        LibraryEntry entry = new LibraryEntry();
        entry.setItem(item);
        entry.setEntryStatus(EntryStatus.PLANNED);

        when(libraryRepository.findById(entryId))
                .thenReturn(Optional.of(entry));

        libraryService.removeFromLibrary(entryId);

        verify(libraryRepository).delete(entry);
    }

    @Test
    void removeFromLibraryThrowsExceptionWhenEntryDoesNotExist() {

        UUID entryId = UUID.randomUUID();

        when(libraryRepository.findById(entryId))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> libraryService.removeFromLibrary(entryId)
        );

        assertEquals(ENTRY_NOT_FOUND, exception.getMessage());

        verify(libraryRepository, never())
                .delete(any(LibraryEntry.class));
    }


    // Get entries tests
    @Test
    void getEntriesByStatusReturnsMatchingEntries() {

        UUID userId = UUID.randomUUID();

        Item item1 = Item.builder()
                .name("The Rookie")
                .build();

        Item item2 = Item.builder()
                .name("Frieren")
                .build();

        LibraryEntry entry1 = new LibraryEntry();
        entry1.setItem(item1);
        entry1.setEntryStatus(EntryStatus.PLANNED);

        LibraryEntry entry2 = new LibraryEntry();
        entry2.setItem(item2);
        entry2.setEntryStatus(EntryStatus.PLANNED);

        List<LibraryEntry> entries =
                List.of(entry1, entry2);

        when(libraryRepository.findByUserIdAndEntryStatus(
                userId,
                EntryStatus.PLANNED
        )).thenReturn(entries);

        List<LibraryEntry> result =
                libraryService.getEntriesByStatus(
                        userId,
                        EntryStatus.PLANNED
                );

        assertEquals(entries, result);
    }


    // Search entries tests
    @Test
    void searchEntriesByStatusReturnsAllEntriesWhenSearchIsBlank() {

        UUID userId = UUID.randomUUID();

        Item item = Item.builder()
                .name("Storm of the Century")
                .build();

        LibraryEntry entry = new LibraryEntry();
        entry.setItem(item);
        entry.setEntryStatus(EntryStatus.PLANNED);

        List<LibraryEntry> entries = List.of(entry);

        when(libraryRepository.findByUserIdAndEntryStatus(
                userId,
                EntryStatus.PLANNED
        )).thenReturn(entries);

        List<LibraryEntry> result =
                libraryService.searchEntriesByStatus(
                        userId,
                        EntryStatus.PLANNED,
                        ""
                );

        assertEquals(entries, result);
    }

    @Test
    void searchEntriesByStatusReturnsMatchingEntriesWhenSearchIsProvided() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        Item item = Item.builder()
                .name("Frieren")
                .build();

        LibraryEntry entry = new LibraryEntry();
        entry.setUser(user);
        entry.setItem(item);
        entry.setEntryStatus(EntryStatus.PLANNED);

        List<LibraryEntry> entries = List.of(entry);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(libraryRepository
                .findByUserAndEntryStatusAndItem_NameContainingIgnoreCase(
                        user,
                        EntryStatus.PLANNED,
                        "fri"
                ))
                .thenReturn(entries);

        List<LibraryEntry> result =
                libraryService.searchEntriesByStatus(
                        userId,
                        EntryStatus.PLANNED,
                        "fri"
                );

        assertEquals(entries, result);
    }

    @Test
    void searchEntriesByStatusThrowsExceptionWhenUserDoesNotExist() {

        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> libraryService.searchEntriesByStatus(
                        userId,
                        EntryStatus.PLANNED,
                        "fri"
                )
        );

        assertEquals(USER_NOT_FOUND, exception.getMessage());
    }
}
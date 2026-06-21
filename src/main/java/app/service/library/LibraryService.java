package app.service.library;

import app.model.entity.item.Item;
import app.model.entity.libraryentry.EntryStatus;
import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.User;
import app.repository.item.ItemRepository;
import app.repository.library.LibraryRepository;
import app.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static app.messages.ErrorMessages.*;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public LibraryService(LibraryRepository libraryRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.libraryRepository = libraryRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    // Error helpers to keep the rest of the methods clean
    private User getUserOrThrow(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        return user;
    }
    private Item getItemOrThrow(UUID itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException(ITEM_NOT_FOUND));

        return item;
    }
    private LibraryEntry getEntryOrThrow(UUID entryId) {
        LibraryEntry entry = libraryRepository.findById(entryId).orElseThrow(() -> new IllegalArgumentException(ENTRY_NOT_FOUND));

        return entry;
    }


    // Find Library item by ID
    public Optional<LibraryEntry> getLibraryEntry(UUID entryId) {
        return libraryRepository.findById(entryId);
    }


    // Add item to personal library: check for user and item and select status to add with
    public void addItemToLibrary(UUID userId, UUID itemId, EntryStatus status) {
        User user = getUserOrThrow(userId);
        Item item = getItemOrThrow(itemId);

        libraryRepository.findByUserAndItem(user, item)
                .ifPresent(i -> {
                   throw new IllegalArgumentException(ITEM_ALREADY_EXISTS_IN_LIBRARY);
                });

        LibraryEntry entry = new LibraryEntry();

        entry.setUser(user);
        entry.setItem(item);
        entry.setEntryStatus(status);

        libraryRepository.save(entry);
    }

    public List<LibraryEntry> getUserLibrary(UUID userId) {
        User user = getUserOrThrow(userId);
        List<LibraryEntry> userLibrary = user.getLibraryEntries();

        return userLibrary;
    }

    // HELPER method to avoid many repeating methods further down because of the 4 statuses
    private List<LibraryEntry> getEntriesListByStatus(UUID userId, EntryStatus status) {
        User user = getUserOrThrow(userId);
        List<LibraryEntry> filteredList = user.getLibraryEntries()
                .stream()
                .filter(entry -> entry.getEntryStatus() == status)
                .toList();

        return filteredList;
    }

    // The 4 methods to get list depending on the filter
    public List<LibraryEntry> getWatchlist(UUID userId) {
        return getEntriesListByStatus(userId, EntryStatus.PLANNED);
    }

    public List<LibraryEntry> getCompleted(UUID userId) {
        return getEntriesListByStatus(userId, EntryStatus.COMPLETED);
    }

    public List<LibraryEntry> getInProgress(UUID userId) {
        return getEntriesListByStatus(userId, EntryStatus.STARTED);
    }

    public List<LibraryEntry> getDropped(UUID userId) {
        return getEntriesListByStatus(userId, EntryStatus.DROPPED);
    }


    // Update an existing entry with new info
    public void updateLibraryEntry(UUID entryId, EntryStatus status, Integer rating, String notes) {
        LibraryEntry entry = getEntryOrThrow(entryId);

        entry.setEntryStatus(status);
        entry.setRating(rating);
        entry.setReviewNotes(notes);

        libraryRepository.save(entry);
    }

    // Remove entry from a library
    public void removeFromLibrary(UUID entryId) {
        LibraryEntry entry = getEntryOrThrow(entryId);

        libraryRepository.delete(entry);
    }
}

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static app.messages.ErrorMessages.*;

@Slf4j
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
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));

        return item;
    }
    private LibraryEntry getEntryOrThrow(UUID entryId) {
        LibraryEntry entry = libraryRepository.findById(entryId).orElseThrow(() -> new IllegalArgumentException(ENTRY_NOT_FOUND));

        return entry;
    }

    // Add item to personal library: check for user and item and select status to add with
    public void addItemToLibrary(UUID userId, UUID itemId, EntryStatus status) {
        log.info("Adding item {} to library for user {} with status {}",
                itemId, userId, status);

        User user = getUserOrThrow(userId);
        Item item = getItemOrThrow(itemId);

        libraryRepository.findByUserAndItem(user, item)
                .ifPresent(i -> {
                   throw new ResourceAlreadyExistsException(ITEM_ALREADY_EXISTS_IN_LIBRARY);
                });

        LibraryEntry entry = new LibraryEntry();

        entry.setUser(user);
        entry.setItem(item);
        entry.setEntryStatus(status);

        libraryRepository.save(entry);
        log.info("Item {} successfully added to library for user {}",
                itemId, userId);
    }

    // Remove entry from a library
    public void removeFromLibrary(UUID entryId) {
        log.info("Removing library entry {}", entryId);

        LibraryEntry entry = getEntryOrThrow(entryId);

        libraryRepository.delete(entry);
        log.info("Library entry {} removed successfully", entryId);
    }


    // Get entries
    public List<LibraryEntry> getEntriesByStatus(UUID userId, EntryStatus status) {
        List<LibraryEntry> entriesByStatus = libraryRepository.findByUserIdAndEntryStatus(userId, status);

        return entriesByStatus;
    }

    // Search entries
    public List<LibraryEntry> searchEntriesByStatus(UUID userId, EntryStatus status, String search) {
        if (search == null || search.isBlank()) {
            return getEntriesByStatus(userId, status);
        }

        User user = getUserOrThrow(userId);

        return libraryRepository
                .findByUserAndEntryStatusAndItem_NameContainingIgnoreCase(
                        user,
                        status,
                        search
                );
    }

    public void changeEntryStatus(UUID entryId, EntryStatus status) {
        LibraryEntry entry = getEntryOrThrow(entryId);

        EntryStatus oldStatus = entry.getEntryStatus();

        entry.setEntryStatus(status);

        libraryRepository.save(entry);
        log.info("Library entry {} status changed from {} to {}",
                entryId, oldStatus, status);
    }
}

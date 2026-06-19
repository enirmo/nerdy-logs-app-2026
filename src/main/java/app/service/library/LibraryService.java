package app.service.library;

import app.model.entity.libraryentry.EntryStatus;
import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.User;
import app.repository.item.ItemRepository;
import app.repository.libraryentry.LibraryEntryRepository;
import app.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibraryService {
    private final LibraryEntryRepository libraryEntryRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public LibraryService(LibraryEntryRepository libraryEntryRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.libraryEntryRepository = libraryEntryRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Optional<LibraryEntry> getLibraryEntry(UUID entryId) {
        return libraryEntryRepository.findById(entryId);
    }

    public void addItemToLibrary(UUID userId, UUID itemId) {
    }

    public List<LibraryEntry> getUserLibrary(UUID userId) {
        return List.of();
    }

    public List<LibraryEntry> getWatchlist(UUID userId) {
        return List.of();
    }

    public List<LibraryEntry> getCompleted(UUID userId) {
        return List.of();
    }

    public void updateLibraryEntry(UUID entryId, EntryStatus status, Integer rating, String notes) {
    }

    public void removeFromLibrary(UUID entryId) {
    }
}

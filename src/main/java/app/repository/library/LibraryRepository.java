package app.repository.library;

import app.model.entity.item.Item;
import app.model.entity.libraryentry.EntryStatus;
import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntry, UUID> {

    Optional<LibraryEntry> findByUserAndItem(User user, Item item);

    List<LibraryEntry> findByUserIdAndEntryStatus(UUID userId, EntryStatus entryStatus);

    List<LibraryEntry> findByUserAndEntryStatusAndItem_NameContainingIgnoreCase(
            User user,
            EntryStatus entryStatus,
            String itemName
    );

}

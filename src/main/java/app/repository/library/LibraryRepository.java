package app.repository.library;

import app.model.entity.item.Item;
import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntry, UUID> {

    Optional<Object> findByItem(Item item);
    Optional<LibraryEntry> findByUserAndItem(User user, Item item);
}

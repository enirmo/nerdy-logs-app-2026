package app.repository.libraryEntry;

import app.model.entity.libraryEntry.LibraryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LibraryEntryRepository extends JpaRepository<LibraryEntry, UUID> {
}

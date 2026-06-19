package app.repository.libraryentry;

import app.model.entity.libraryentry.LibraryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LibraryEntryRepository extends JpaRepository<LibraryEntry, UUID> {
}

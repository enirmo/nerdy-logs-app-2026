package app.model.dto.user;

import app.model.entity.libraryentry.LibraryEntry;
import app.model.entity.user.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String username;
    private String profilePicture;
    private String email;
    private Role role;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<LibraryEntry> libraryEntries;
}

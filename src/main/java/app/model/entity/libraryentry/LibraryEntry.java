package app.model.entity.libraryentry;

import app.model.entity.item.Item;
import app.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lib_entries")
public class LibraryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // ONE OF: COMPLETED, STARTED, DROPPED, PLANNED
    @Enumerated(EnumType.STRING)
    private EntryStatus entryStatus;

    private Integer rating;

    private String reviewNotes;

    private LocalDateTime addedOn;
    private LocalDateTime completedOn;
}

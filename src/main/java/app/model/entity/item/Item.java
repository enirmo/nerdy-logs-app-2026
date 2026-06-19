package app.model.entity.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String pictureCover;

    @NotNull
    @Column(unique = true)
    private String name;

    private String description;
    private Integer releaseYear;

    // ONE OF: GAME, SERIES, MOVIE, ANIME, BOOK
    @NotNull
    @Enumerated(EnumType.STRING)
    private Medium mediumType;

    // ONE OF: CRIME, THRILLER, HORROR, COMEDY, ACTION, ROMANCE, FANTASY, OTHER
    @NotNull
    @Enumerated(EnumType.STRING)
    private Genre genre;

    //TODO: Map the table properly/set up relationships
    // private List<String> tags = new ArrayList<>();

}

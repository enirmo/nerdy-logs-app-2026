package app.model.dto.item;

import app.model.entity.item.Genre;
import app.model.entity.item.Medium;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import static app.messages.ErrorMessages.*;

@Builder
@Data
public class ItemRequest {
    @NotBlank
    @Size(min = 1, max = 255, message = ENTRY_NAME_SIZE)
    private String itemName;

    @NotNull(message = MEDIUM_REQUIRED)
    private Medium medium;

    @NotNull(message = GENRE_REQUIRED)
    private Genre genre;

    private String description;
    private String pictureCover;
    private Integer releaseYear;
}

package app.model.dto.item;

import app.model.entity.item.Genre;
import app.model.entity.item.Medium;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemRequest {
    @NotBlank
    @Size(min = 1, max = 255, message = "Entry must have a name with less than 255 characters")
    private String itemName;

    @NotNull(message = "You must choose a category/medium")
    private Medium medium;

    @NotNull(message = "You must select a genre")
    private Genre genre;

    private String description;
    private String pictureCover;
    private Integer releaseYear;
}

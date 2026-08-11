package app.model.dto;

import app.messages.ErrorMessages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewCreateRequest {

    private UUID userId;

    private UUID mediaId;

    @Min(value = 1, message = ErrorMessages.RATING_MIN)
    @Max(value = 10, message = ErrorMessages.RATING_MAX)
    private int rating;

    private String comment;
}

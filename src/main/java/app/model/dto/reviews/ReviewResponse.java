package app.model.dto.reviews;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import static app.messages.ErrorMessages.RATING_MAX;
import static app.messages.ErrorMessages.RATING_MIN;

@Builder
@Data
public class ReviewResponse {
    private UUID id;

    @Min(value = 1, message = RATING_MIN)
    @Max(value = 10, message = RATING_MAX)
    private int rating;

    private String comment;
    private LocalDateTime lastUpdateTime;
}
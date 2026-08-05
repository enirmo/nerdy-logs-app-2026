package app.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ReviewCreateRequest {

    private UUID userId;

    private UUID mediaId;

    @Min(1)
    @Max(10)
    private int rating;

    private String comment;
}

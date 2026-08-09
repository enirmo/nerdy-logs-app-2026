package app.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ReviewResponse {
     private UUID id;
     private int rating;
     private String comment;
     private LocalDateTime lastUpdateTime;
}

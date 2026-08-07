package app.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ReviewResponse {
     private int rating;
     private String comment;
     private LocalDateTime lastUpdateTime;
}

package app.client;

import app.model.dto.reviews.ReviewCreateRequest;
import app.model.dto.reviews.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "review-service",
        url = "${review-service.url}"
)
public interface ReviewClient {

    @PostMapping("/api/reviews")
    void addReview(@RequestBody ReviewCreateRequest request);

    @PutMapping("/api/reviews")
    void editReview(@RequestBody ReviewCreateRequest request);

    @DeleteMapping("/api/reviews/{reviewId}")
    void deleteReview(@PathVariable UUID reviewId);

    @GetMapping("/api/reviews/user/{userId}/media/{mediaId}")
    ReviewResponse getReview(
            @PathVariable UUID userId,
            @PathVariable UUID mediaId
    );

    @GetMapping("/api/reviews/media/{mediaId}/average")
    Double getAverageReviews(@PathVariable UUID mediaId);
}

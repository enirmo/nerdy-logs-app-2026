package app.service.reviews;

import app.client.ReviewClient;
import app.model.dto.reviews.ReviewCreateRequest;
import app.model.dto.reviews.ReviewResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ReviewIntegrationService {

    private final ReviewClient reviewClient;

    public ReviewIntegrationService(ReviewClient reviewClient) {
        this.reviewClient = reviewClient;
    }

    public void addReview(ReviewCreateRequest request) {
        reviewClient.addReview(request);
    }

    public void editReview(ReviewCreateRequest request) {
        reviewClient.editReview(request);
    }

    public void deleteReview(UUID reviewId) {
        reviewClient.deleteReview(reviewId);
    }

    public ReviewResponse getReview(UUID userId, UUID mediaId) {
        try {
            return reviewClient.getReview(userId, mediaId);
        } catch (FeignException exception) {
            log.warn("Could not retrieve review for user {} and media {}",
                    userId, mediaId);

            return null;
        }
    }

    public Double getAverageReviews(UUID mediaId) {
        try {
            return reviewClient.getAverageReviews(mediaId);
        } catch (FeignException exception) {
            log.warn("Could not retrieve average rating for media {}",
                    mediaId);

            return 0.0;
        }
    }
}

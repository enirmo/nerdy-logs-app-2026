package app.service.reviews;

import app.client.ReviewClient;
import app.model.dto.reviews.ReviewCreateRequest;
import app.model.dto.reviews.ReviewResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        return reviewClient.getReview(userId, mediaId);
    }

    public Double getAverageReviews(UUID mediaId) {
        return reviewClient.getAverageReviews(mediaId);
    }
}

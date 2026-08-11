package app.integration;

import app.model.dto.ReviewCreateRequest;
import app.model.dto.ReviewResponse;
import app.repository.ReviewRepository;
import app.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;


    // Review integration tests
    @Test
    void addReviewCanBeRetrievedFromDatabase() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(9)
                .comment("Great")
                .build();

        reviewService.addReview(request);

        Optional<ReviewResponse> result =
                reviewService.getReview(userId, mediaId);

        assertTrue(result.isPresent());

        ReviewResponse review = result.get();

        assertEquals(9, review.getRating());
        assertEquals("Great", review.getComment());
    }
}
package app.service.reviews;

import app.client.ReviewClient;
import app.model.dto.reviews.ReviewCreateRequest;
import app.model.dto.reviews.ReviewResponse;
import app.service.reviews.ReviewIntegrationService;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewIntegrationServiceTest {

    @Mock
    private ReviewClient reviewClient;

    @InjectMocks
    private ReviewIntegrationService reviewIntegrationService;


    // Add review tests
    @Test
    void addReviewSendsReviewToClient() {

        ReviewCreateRequest request =
                mock(ReviewCreateRequest.class);

        reviewIntegrationService.addReview(request);

        verify(reviewClient).addReview(request);
    }


    // Edit review tests
    @Test
    void editReviewSendsUpdatedReviewToClient() {

        ReviewCreateRequest request =
                mock(ReviewCreateRequest.class);

        reviewIntegrationService.editReview(request);

        verify(reviewClient).editReview(request);
    }


    // Delete review tests
    @Test
    void deleteReviewDeletesReviewThroughClient() {

        UUID reviewId = UUID.randomUUID();

        reviewIntegrationService.deleteReview(reviewId);

        verify(reviewClient).deleteReview(reviewId);
    }


    // Get review tests
    @Test
    void getReviewReturnsReviewWhenReviewExists() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        ReviewResponse review =
                mock(ReviewResponse.class);

        when(reviewClient.getReview(userId, mediaId))
                .thenReturn(review);

        ReviewResponse result =
                reviewIntegrationService.getReview(
                        userId,
                        mediaId
                );

        assertEquals(review, result);
    }

    @Test
    void getReviewReturnsNullWhenReviewDoesNotExist() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        FeignException.NotFound exception =
                mock(FeignException.NotFound.class);

        when(reviewClient.getReview(userId, mediaId))
                .thenThrow(exception);

        ReviewResponse result =
                reviewIntegrationService.getReview(
                        userId,
                        mediaId
                );

        assertNull(result);
    }


    // Get average review tests
    @Test
    void getAverageReviewsReturnsAverageRating() {

        UUID mediaId = UUID.randomUUID();

        when(reviewClient.getAverageReviews(mediaId))
                .thenReturn(8.5);

        Double result =
                reviewIntegrationService.getAverageReviews(mediaId);

        assertEquals(8.5, result);
    }
}
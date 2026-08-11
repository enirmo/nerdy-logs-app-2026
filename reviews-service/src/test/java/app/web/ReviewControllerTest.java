package app.web;

import app.model.dto.ReviewCreateRequest;
import app.model.dto.ReviewResponse;
import app.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;


    // Add review tests
    @Test
    void addReviewReturnsCreatedWhenDataIsValid() {

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(UUID.randomUUID())
                .mediaId(UUID.randomUUID())
                .rating(9)
                .comment("Loved it")
                .build();

        ResponseEntity<Void> response =
                reviewController.addReview(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(reviewService).addReview(request);
    }


    // Edit review tests
    @Test
    void editReviewReturnsOkWhenDataIsValid() {

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(UUID.randomUUID())
                .mediaId(UUID.randomUUID())
                .rating(10)
                .comment("Even better")
                .build();

        ResponseEntity<Void> response =
                reviewController.editReview(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(reviewService).editReview(request);
    }


    // Delete review tests
    @Test
    void deleteReviewReturnsNoContentWhenReviewExists() {

        UUID reviewId = UUID.randomUUID();

        ResponseEntity<Void> response =
                reviewController.deleteReview(reviewId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(reviewService).deleteReview(reviewId);
    }


    // Get review tests
    @Test
    void getReviewReturnsOkWhenReviewExists() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        ReviewResponse review = ReviewResponse.builder()
                .id(UUID.randomUUID())
                .rating(9)
                .comment("Great")
                .lastUpdateTime(LocalDateTime.now())
                .build();

        when(reviewService.getReview(userId, mediaId))
                .thenReturn(Optional.of(review));

        ResponseEntity<ReviewResponse> response =
                reviewController.getReview(userId, mediaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());
    }

    @Test
    void getReviewReturnsNotFoundWhenReviewDoesNotExist() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        when(reviewService.getReview(userId, mediaId))
                .thenReturn(Optional.empty());

        ResponseEntity<ReviewResponse> response =
                reviewController.getReview(userId, mediaId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }


    // Get average review tests
    @Test
    void getAverageReviewsReturnsAverage() {

        UUID mediaId = UUID.randomUUID();

        when(reviewService.getAverageReviews(mediaId))
                .thenReturn(8.5);

        ResponseEntity<Double> response =
                reviewController.getAverageReviews(mediaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(8.5, response.getBody());
    }
}
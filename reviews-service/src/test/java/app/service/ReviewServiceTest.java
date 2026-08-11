package app.service;

import app.exceptions.ReviewAlreadyExistsException;
import app.exceptions.ReviewNotFoundException;
import app.messages.ErrorMessages;
import app.model.dto.ReviewCreateRequest;
import app.model.dto.ReviewResponse;
import app.model.entity.Review;
import app.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewService reviewService;


    // Add review tests
    @Test
    void addReviewCreatesReviewWhenReviewDoesNotExist() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(9)
                .comment("Loved it")
                .build();

        when(reviewRepository.existsByUserIdAndMediaId(userId, mediaId))
                .thenReturn(false);

        reviewService.addReview(request);

        ArgumentCaptor<Review> reviewCaptor =
                ArgumentCaptor.forClass(Review.class);

        verify(reviewRepository).save(reviewCaptor.capture());

        Review savedReview = reviewCaptor.getValue();

        assertEquals(userId, savedReview.getUserId());
        assertEquals(mediaId, savedReview.getMediaId());
        assertEquals(9, savedReview.getRating());
        assertEquals("Loved it", savedReview.getComment());
        assertNotNull(savedReview.getLastUpdateTime());
    }

    @Test
    void addReviewThrowsExceptionWhenReviewAlreadyExists() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(9)
                .comment("Loved it")
                .build();

        when(reviewRepository.existsByUserIdAndMediaId(userId, mediaId))
                .thenReturn(true);

        ReviewAlreadyExistsException exception = assertThrows(
                ReviewAlreadyExistsException.class,
                () -> reviewService.addReview(request)
        );

        assertEquals(
                ErrorMessages.REVIEW_ALREADY_EXISTS,
                exception.getMessage()
        );

        verify(reviewRepository, never())
                .save(any(Review.class));
    }


    // Edit review tests
    @Test
    void editReviewUpdatesReviewWhenReviewExists() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        Review review = Review.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(7)
                .comment("Old comment")
                .lastUpdateTime(LocalDateTime.now().minusDays(1))
                .build();

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(10)
                .comment("Much better")
                .build();

        when(reviewRepository.findByUserIdAndMediaId(userId, mediaId))
                .thenReturn(Optional.of(review));

        LocalDateTime oldUpdateTime = review.getLastUpdateTime();

        reviewService.editReview(request);

        assertEquals(10, review.getRating());
        assertEquals("Much better", review.getComment());
        assertTrue(review.getLastUpdateTime().isAfter(oldUpdateTime));

        verify(reviewRepository).save(review);
    }

    @Test
    void editReviewThrowsExceptionWhenReviewDoesNotExist() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(8)
                .comment("Comment")
                .build();

        when(reviewRepository.findByUserIdAndMediaId(userId, mediaId))
                .thenReturn(Optional.empty());

        ReviewNotFoundException exception = assertThrows(
                ReviewNotFoundException.class,
                () -> reviewService.editReview(request)
        );

        assertEquals(
                ErrorMessages.REVIEW_NOT_FOUND,
                exception.getMessage()
        );

        verify(reviewRepository, never())
                .save(any(Review.class));
    }


    // Delete review tests
    @Test
    void deleteReviewDeletesReviewWhenReviewExists() {

        UUID reviewId = UUID.randomUUID();

        Review review = Review.builder()
                .id(reviewId)
                .rating(9)
                .comment("Great")
                .build();

        when(reviewRepository.findById(reviewId))
                .thenReturn(Optional.of(review));

        reviewService.deleteReview(reviewId);

        verify(reviewRepository).delete(review);
    }

    @Test
    void deleteReviewThrowsExceptionWhenReviewDoesNotExist() {

        UUID reviewId = UUID.randomUUID();

        when(reviewRepository.findById(reviewId))
                .thenReturn(Optional.empty());

        ReviewNotFoundException exception = assertThrows(
                ReviewNotFoundException.class,
                () -> reviewService.deleteReview(reviewId)
        );

        assertEquals(
                ErrorMessages.REVIEW_NOT_FOUND,
                exception.getMessage()
        );

        verify(reviewRepository, never())
                .delete(any(Review.class));
    }


    // Get review tests
    @Test
    void getReviewReturnsResponseWhenReviewExists() {

        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        LocalDateTime updateTime = LocalDateTime.now();

        Review review = Review.builder()
                .id(reviewId)
                .userId(userId)
                .mediaId(mediaId)
                .rating(10)
                .comment("Amazing")
                .lastUpdateTime(updateTime)
                .build();

        when(reviewRepository.findByUserIdAndMediaId(userId, mediaId))
                .thenReturn(Optional.of(review));

        Optional<ReviewResponse> result =
                reviewService.getReview(userId, mediaId);

        assertTrue(result.isPresent());

        ReviewResponse response = result.get();

        assertEquals(reviewId, response.getId());
        assertEquals(10, response.getRating());
        assertEquals("Amazing", response.getComment());
        assertEquals(updateTime, response.getLastUpdateTime());
    }

    @Test
    void getReviewReturnsEmptyWhenReviewDoesNotExist() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        when(reviewRepository.findByUserIdAndMediaId(userId, mediaId))
                .thenReturn(Optional.empty());

        Optional<ReviewResponse> result =
                reviewService.getReview(userId, mediaId);

        assertTrue(result.isEmpty());
    }


    // Average review tests
    @Test
    void getAverageReviewsReturnsAverageWhenReviewsExist() {

        UUID mediaId = UUID.randomUUID();

        Review review1 = Review.builder()
                .rating(8)
                .build();

        Review review2 = Review.builder()
                .rating(10)
                .build();

        Review review3 = Review.builder()
                .rating(6)
                .build();

        when(reviewRepository.findAllByMediaId(mediaId))
                .thenReturn(List.of(
                        review1,
                        review2,
                        review3
                ));

        double result =
                reviewService.getAverageReviews(mediaId);

        assertEquals(8.0, result);
    }

    @Test
    void getAverageReviewsReturnsZeroWhenNoReviewsExist() {

        UUID mediaId = UUID.randomUUID();

        when(reviewRepository.findAllByMediaId(mediaId))
                .thenReturn(List.of());

        double result =
                reviewService.getAverageReviews(mediaId);

        assertEquals(0.0, result);
    }
}
package app.service;

import app.exceptions.ReviewAlreadyExistsException;
import app.exceptions.ReviewNotFoundException;
import app.messages.ErrorMessages;
import app.model.dto.ReviewCreateRequest;
import app.model.dto.ReviewResponse;
import app.model.entity.Review;
import app.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final List<Integer> ratings;

    public ReviewService(ReviewRepository reviewRepository, List<Integer> ratings) {
        this.reviewRepository = reviewRepository;
        this.ratings = ratings;
    }

    // 1. Add review (only to existing library items, ONLY if no review already)
    public void addReview(ReviewCreateRequest request) {
        if (reviewRepository.existsByUserIdAndMediaId(request.getUserId(), request.getMediaId())) {
            throw new ReviewAlreadyExistsException(ErrorMessages.REVIEW_ALREADY_EXISTS);
        }

        LocalDateTime now = LocalDateTime.now();

        Review review = Review.builder()
                .userId(request.getUserId())
                .mediaId(request.getMediaId())
                .rating(request.getRating())
                .comment(request.getComment())
                .lastUpdateTime(now)
                .build();

        reviewRepository.save(review);
    }

    // 2. Edit review (only if exists)
    public void editReview(ReviewCreateRequest request) {
        Review review = reviewRepository
                .findByUserIdAndMediaId(request.getUserId(), request.getMediaId())
                .orElseThrow(() -> new ReviewNotFoundException(ErrorMessages.REVIEW_NOT_FOUND));

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setLastUpdateTime(LocalDateTime.now());

        reviewRepository.save(review);
    }

    // 3. Delete review (only if exists)
    public void deleteReview(UUID reviewId) {
        Review reviewToDelete = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(ErrorMessages.REVIEW_NOT_FOUND));

        reviewRepository.delete(reviewToDelete);
    }

    // 4. Get review
    public Optional<ReviewResponse> getReview(UUID userId, UUID mediaId) {

        return reviewRepository
                .findByUserIdAndMediaId(userId, mediaId)
                .map(review -> ReviewResponse.builder()
                        .id(review.getId())
                        .rating(review.getRating())
                        .comment(review.getComment())
                        .lastUpdateTime(review.getLastUpdateTime())
                        .build());
    }

    // 5. Get average of reviews for this media
    public double getAverageReviews(UUID mediaID) {
        List<Review> reviewsList = reviewRepository.findAllByMediaId(mediaID);
        double sum = 0.0;

        if (reviewsList.isEmpty()) {
            return 0.0;
        }

        for (Review review : reviewsList) {
            sum += review.getRating();
        }

        return sum / reviewsList.size();
    }
}

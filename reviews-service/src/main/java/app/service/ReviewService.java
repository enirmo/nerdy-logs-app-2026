package app.service;

import app.model.dto.ReviewCreateRequest;
import app.model.entity.Review;
import app.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/*
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // 1. Add review (only to existing library items, ONLY if no review already)
    public void addReview(ReviewCreateRequest request) {
        Review review = reviewRepository.findByUserIdAndMediaId(request.getUserId(), request.getMediaId());

        if (review != null) {
            throw new IllegalArgumentException("Review already exists");
        }

        LocalDateTime now = LocalDateTime.now();

        review = Review.builder()
                .userId(request.getUserId())
                .mediaId(request.getMediaId())
                .rating(request.getRating())
                .comment(request.getComment())
                .lastUpdateTime(now)
                .build();

        reviewRepository.save(review);
    }

    // 2. Edit review (only if exists)
    public void EditReview(ReviewCreateRequest request) {
        Review review = reviewRepository.findByUserIdAndMediaId(request.getUserId(), request.getMediaId());

        if (review == null) {
            throw new IllegalArgumentException("Review doesn't exist");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setLastUpdateTime(LocalDateTime.now());

        reviewRepository.save(review);
    }

    // 3. Delete review (only if exists)
    public void deleteReview(Review review) {
        Review reviewToDelete = reviewRepository.findById(review.getId()).orElseThrow(() -> new IllegalArgumentException("Review not found"));

        reviewRepository.delete(reviewToDelete);
    }
}
*/
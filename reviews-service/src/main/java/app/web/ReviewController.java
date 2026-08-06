package app.web;

import app.model.dto.ReviewCreateRequest;
import app.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Void> addReview(
            @RequestBody @Valid ReviewCreateRequest request) {

        reviewService.addReview(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> editReview(
            @RequestBody @Valid ReviewCreateRequest request) {

        reviewService.editReview(request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable UUID reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.noContent().build();
    }
}
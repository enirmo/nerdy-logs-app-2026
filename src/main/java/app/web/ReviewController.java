package app.web;

import app.model.dto.reviews.ReviewCreateRequest;
import app.service.reviews.ReviewIntegrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewIntegrationService reviewIntegrationService;

    public ReviewController(ReviewIntegrationService reviewIntegrationService) {
        this.reviewIntegrationService = reviewIntegrationService;
    }

    @PostMapping("/add")
    public String addReview(
            @RequestParam UUID mediaId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment,
            @RequestParam String redirectTo,
            HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            return "redirect:/sign-in";
        }

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(rating)
                .comment(comment)
                .build();

        reviewIntegrationService.addReview(request);

        return "redirect:" + redirectTo;
    }

    @PostMapping("/edit")
    public String editReview(
            @RequestParam UUID mediaId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment,
            @RequestParam String redirectTo,
            HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");

        if (userId == null) {
            return "redirect:/sign-in";
        }

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(rating)
                .comment(comment)
                .build();

        reviewIntegrationService.editReview(request);

        return "redirect:" + redirectTo;
    }

    @PostMapping("/delete")
    public String deleteReview(
            @RequestParam UUID reviewId,
            @RequestParam String redirectTo) {

        reviewIntegrationService.deleteReview(reviewId);

        return "redirect:" + redirectTo;
    }
}
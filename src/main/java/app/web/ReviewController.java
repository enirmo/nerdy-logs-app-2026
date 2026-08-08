package app.web;

import app.model.dto.reviews.ReviewCreateRequest;
import app.service.reviews.ReviewIntegrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/reviews")
public class ReviewController {

    private final ReviewIntegrationService reviewIntegrationService;

    public ReviewController(ReviewIntegrationService reviewIntegrationService) {
        this.reviewIntegrationService = reviewIntegrationService;
    }

    @PostMapping
    public void addReview(@RequestBody ReviewCreateRequest request) {
        reviewIntegrationService.addReview(request);
    }
}

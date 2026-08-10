package app.web;

import app.messages.ErrorMessages;
import app.model.dto.reviews.ReviewCreateRequest;
import app.model.entity.user.User;
import app.service.reviews.ReviewIntegrationService;
import app.service.user.UserService;
import feign.FeignException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewIntegrationService reviewIntegrationService;
    private final UserService userService;

    public ReviewController(ReviewIntegrationService reviewIntegrationService, UserService userService) {
        this.reviewIntegrationService = reviewIntegrationService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addReview(
            @RequestParam UUID mediaId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment,
            @RequestParam String redirectTo,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        User user = userService.getByUsername(authentication.getName());
        UUID userId = user.getId();

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(rating)
                .comment(comment)
                .build();

        try {
            reviewIntegrationService.addReview(request);

        } catch (FeignException exception) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ErrorMessages.REVIEW_CREATE_FAILED
            );
        }

        return "redirect:" + redirectTo;
    }

    @PostMapping("/edit")
    public String editReview(
            @RequestParam UUID mediaId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment,
            @RequestParam String redirectTo,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        User user = userService.getByUsername(authentication.getName());
        UUID userId = user.getId();

        if (userId == null) {
            return "redirect:/sign-in";
        }

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .userId(userId)
                .mediaId(mediaId)
                .rating(rating)
                .comment(comment)
                .build();

        try {
            reviewIntegrationService.editReview(request);

        } catch (FeignException exception) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ErrorMessages.REVIEW_UPDATE_FAILED
            );
        }

        return "redirect:" + redirectTo;
    }

    @PostMapping("/delete")
    public String deleteReview(
            @RequestParam UUID reviewId,
            @RequestParam String redirectTo,
            RedirectAttributes redirectAttributes) {

        try {
            reviewIntegrationService.deleteReview(reviewId);

        } catch (FeignException exception) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ErrorMessages.REVIEW_DELETE_FAILED
            );
        }

        return "redirect:" + redirectTo;
    }
}
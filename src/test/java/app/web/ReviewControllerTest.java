package app.web;

import app.messages.ErrorMessages;
import app.model.entity.user.User;
import app.service.reviews.ReviewIntegrationService;
import app.service.user.UserService;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {
    @Mock
    private ReviewIntegrationService reviewIntegrationService;
    @Mock
    private UserService userService;
    @Mock
    private Authentication authentication;
    @Mock
    private RedirectAttributes redirectAttributes;
    @InjectMocks
    private ReviewController reviewController;


    // Add review tests
    @Test
    void addReviewAddsReviewAndRedirectsWhenDataIsValid() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        String result = reviewController.addReview(
                mediaId,
                9,
                "Great",
                "/books",
                authentication,
                redirectAttributes
        );

        assertEquals("redirect:/books", result);

        verify(reviewIntegrationService).addReview(
                argThat(request ->
                        request.getUserId().equals(userId)
                                && request.getMediaId().equals(mediaId)
                                && request.getRating() == 9
                                && request.getComment().equals("Great")
                )
        );
    }

    @Test
    void addReviewShowsErrorWhenFeignFails() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        doThrow(mock(FeignException.class))
                .when(reviewIntegrationService)
                .addReview(any());

        String result = reviewController.addReview(
                mediaId,
                8,
                "Nice",
                "/anime",
                authentication,
                redirectAttributes
        );

        assertEquals("redirect:/anime", result);

        verify(redirectAttributes).addFlashAttribute(
                "errorMessage",
                ErrorMessages.REVIEW_CREATE_FAILED
        );
    }


    // Edit review tests
    @Test
    void editReviewEditsReviewAndRedirectsWhenDataIsValid() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        String result = reviewController.editReview(
                mediaId,
                10,
                "Even better",
                "/completed",
                authentication,
                redirectAttributes
        );

        assertEquals("redirect:/completed", result);

        verify(reviewIntegrationService).editReview(
                argThat(request ->
                        request.getUserId().equals(userId)
                                && request.getMediaId().equals(mediaId)
                                && request.getRating() == 10
                                && request.getComment().equals("Even better")
                )
        );
    }

    @Test
    void editReviewRedirectsToSignInWhenUserIdIsNull() {

        User user = User.builder()
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        String result = reviewController.editReview(
                UUID.randomUUID(),
                7,
                "Comment",
                "/watchlist",
                authentication,
                redirectAttributes
        );

        assertEquals("redirect:/sign-in", result);

        verify(reviewIntegrationService, never())
                .editReview(any());
    }

    @Test
    void editReviewShowsErrorWhenFeignFails() {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("user")
                .build();

        when(authentication.getName())
                .thenReturn("user");

        when(userService.getByUsername("user"))
                .thenReturn(user);

        doThrow(mock(FeignException.class))
                .when(reviewIntegrationService)
                .editReview(any());

        String result = reviewController.editReview(
                mediaId,
                6,
                "Changed",
                "/in-progress",
                authentication,
                redirectAttributes
        );

        assertEquals("redirect:/in-progress", result);

        verify(redirectAttributes).addFlashAttribute(
                "errorMessage",
                ErrorMessages.REVIEW_UPDATE_FAILED
        );
    }


    // Delete review tests
    @Test
    void deleteReviewDeletesReviewAndRedirects() {

        UUID reviewId = UUID.randomUUID();

        String result = reviewController.deleteReview(
                reviewId,
                "/completed",
                redirectAttributes
        );

        assertEquals("redirect:/completed", result);

        verify(reviewIntegrationService)
                .deleteReview(reviewId);
    }

    @Test
    void deleteReviewShowsErrorWhenFeignFails() {

        UUID reviewId = UUID.randomUUID();

        doThrow(mock(FeignException.class))
                .when(reviewIntegrationService)
                .deleteReview(reviewId);

        String result = reviewController.deleteReview(
                reviewId,
                "/completed",
                redirectAttributes
        );

        assertEquals("redirect:/completed", result);

        verify(redirectAttributes).addFlashAttribute(
                "errorMessage",
                ErrorMessages.REVIEW_DELETE_FAILED
        );
    }
}
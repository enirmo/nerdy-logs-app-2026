package app.api;

import app.service.ReviewService;
import app.web.ReviewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;


    // Get review API tests
    @Test
    void getReviewReturnsNotFoundWhenReviewDoesNotExist() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        when(reviewService.getReview(userId, mediaId))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/api/reviews/user/{userId}/media/{mediaId}",
                                userId,
                                mediaId
                        )
                )
                .andExpect(status().isNotFound());
    }
}
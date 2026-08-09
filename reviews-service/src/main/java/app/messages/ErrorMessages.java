package app.messages;

public final class ErrorMessages {

    public static final String REVIEW_ALREADY_EXISTS = "Review already exists.";
    public static final String REVIEW_NOT_FOUND = "Review not found.";
    public static final String RATING_MIN = "Rating must be at least 1.";
    public static final String RATING_MAX = "Rating cannot be greater than 10.";

    private ErrorMessages() {
    }
}
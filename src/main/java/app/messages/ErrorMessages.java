package app.messages;

public class ErrorMessages {

    public static final String USER_NOT_FOUND = "User not found.";
    public static final String ITEM_NOT_FOUND = "Item not found.";
    public static final String ENTRY_NOT_FOUND = "Entry not found.";
    public static final String ITEM_ALREADY_ADDED = "%s is already added.";
    public static final String ITEM_ALREADY_EXISTS_IN_LIBRARY = "Item already exists in library.";
    public static final String USERNAME_TAKEN = "Username is taken.";
    public static final String EMAIL_ALREADY_REGISTERED = "Email already registered.";
    public static final String USERNAME_DOES_NOT_EXIST = "Username doesn't exist.";
    public static final String WRONG_PASSWORD = "Wrong password.";
    public static final String ENTRY_NAME_SIZE = "Entry must have a name with less than 255 characters";
    public static final String MEDIUM_REQUIRED = "You must choose a category/medium";
    public static final String GENRE_REQUIRED = "You must select a genre";
    public static final String USERNAME_SIZE = "Username must be at least 4 and max 20 characters.";
    public static final String PASSWORD_SIZE = "Password must be at least 6 characters.";
    public static final String USERNAME_REQUIRED = "Username is required.";
    public static final String PASSWORD_REQUIRED = "Password is required.";
    public static final String EMAIL_REQUIRED = "Email is required.";
    public static final String INVALID_EMAIL = "Please enter a valid email address.";
    public static final String INVALID_CREDENTIALS = "Invalid username or password.";
    public static final String RATING_MIN = "Rating must be at least 1.";
    public static final String RATING_MAX = "Rating cannot be greater than 10.";
    public static final String REVIEW_CREATE_FAILED = "The review could not be saved.";
    public static final String REVIEW_UPDATE_FAILED = "The review could not be updated.";
    public static final String REVIEW_DELETE_FAILED = "The review could not be deleted.";


    private ErrorMessages() {
    }
}

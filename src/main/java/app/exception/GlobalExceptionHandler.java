package app.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleUnexpectedException(
            Exception exception,
            Model model) {

        model.addAttribute(
                "errorMessage",
                "Something went wrong. Please try again."
        );

        return "error";
    }
}

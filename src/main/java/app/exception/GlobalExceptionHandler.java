package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            Model model) {

        model.addAttribute(
                "errorMessage",
                "The request contains an invalid value."
        );

        return "error";
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleItemNotFound(
            ItemNotFoundException exception,
            Model model) {

        model.addAttribute(
                "errorMessage",
                exception.getMessage()
        );

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnexpectedException(
            Exception exception,
            Model model) {

        model.addAttribute(
                "errorMessage",
                exception.getMessage()
        );

        return "error";
    }
}

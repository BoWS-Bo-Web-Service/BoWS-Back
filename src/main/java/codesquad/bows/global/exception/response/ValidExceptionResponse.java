package codesquad.bows.global.exception.response;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public record ValidExceptionResponse(String errorField, String errorMessage) {

    public static ValidExceptionResponse from (MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();

        if (fieldError != null) {
            String errorField = String.format("%s.%s", fieldError.getObjectName(), fieldError.getField());
            String errorMessage = fieldError.getDefaultMessage();
            return new ValidExceptionResponse(errorField, errorMessage);
        }

        return new ValidExceptionResponse(e.getClass().getSimpleName(), e.getMessage());
    }
}

package codesquad.bows.global.exception.response;

import org.springframework.security.access.AccessDeniedException;

public record AccessDeniedExceptionResponse(String exceptionClass, String errorMessage) {

    public static AccessDeniedExceptionResponse from(AccessDeniedException e){
        String exceptionClass = e.getClass().getSimpleName();
        String errorMessage = e.getMessage();

        return new AccessDeniedExceptionResponse(exceptionClass, errorMessage);
    }

}

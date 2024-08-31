package codesquad.bows.global.exception;

import codesquad.bows.global.exception.response.AccessDeniedExceptionResponse;
import codesquad.bows.global.exception.response.BusinessExceptionResponse;
import codesquad.bows.global.exception.response.ValidExceptionResponse;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        ValidExceptionResponse response = ValidExceptionResponse.from(e);

        log.error(response.toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessExceptionResponse> handleBusinessException(BusinessException e) {
        BusinessExceptionResponse response = BusinessExceptionResponse.from(e);

        log.error(response.toString());

        return new ResponseEntity<>(response, e.getExceptionType().getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        AccessDeniedExceptionResponse response = AccessDeniedExceptionResponse.from(e);

        log.error(response.toString());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("올바르지 않은 토큰입니다.");
    }
}

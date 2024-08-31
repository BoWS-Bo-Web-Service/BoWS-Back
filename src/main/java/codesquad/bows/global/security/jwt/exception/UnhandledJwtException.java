package codesquad.bows.global.security.jwt.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class UnhandledJwtException extends BusinessException {
    public UnhandledJwtException() {
        super(ExceptionType.UNHANDLED_TOKEN_EXCEPTION);
    }
}

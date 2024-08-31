package codesquad.bows.global.security.jwt.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super(ExceptionType.INVALID_TOKEN);
    }
}


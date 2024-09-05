package codesquad.bows.global.security.jwt.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class ExpiredTokenException extends BusinessException {
    public ExpiredTokenException() {
        super(ExceptionType.EXPIRED_TOKEN);
    }
}

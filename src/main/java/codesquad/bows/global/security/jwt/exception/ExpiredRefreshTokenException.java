package codesquad.bows.global.security.jwt.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class ExpiredRefreshTokenException extends BusinessException {
    public ExpiredRefreshTokenException() {
        super(ExceptionType.EXPIRED_REFRESH_TOKEN);
    }
}

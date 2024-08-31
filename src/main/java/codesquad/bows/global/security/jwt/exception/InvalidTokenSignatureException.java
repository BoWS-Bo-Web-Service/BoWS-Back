package codesquad.bows.global.security.jwt.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class InvalidTokenSignatureException extends BusinessException {
    public InvalidTokenSignatureException() {
        super(ExceptionType.INVALID_TOKEN_SIGNATURE);
    }
}

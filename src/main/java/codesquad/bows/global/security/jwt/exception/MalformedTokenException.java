package codesquad.bows.global.security.jwt.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class MalformedTokenException extends BusinessException {
    public MalformedTokenException() {
        super(ExceptionType.MALFORMED_TOKEN);
    }
}

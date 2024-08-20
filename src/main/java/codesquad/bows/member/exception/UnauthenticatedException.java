package codesquad.bows.member.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class UnauthenticatedException extends BusinessException {
    public UnauthenticatedException() {
        super(ExceptionType.UNAUTHENTICATED_USER);
    }
}
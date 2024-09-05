package codesquad.bows.member.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class UnhandledAuthenticationException extends BusinessException {
    public UnhandledAuthenticationException(){
        super(ExceptionType.UNHANDLED_AUTHENTICATION_EXCEPTION);
    }
}

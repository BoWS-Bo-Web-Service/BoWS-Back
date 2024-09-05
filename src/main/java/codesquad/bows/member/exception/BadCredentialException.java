package codesquad.bows.member.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class BadCredentialException extends BusinessException {
    public BadCredentialException(){
        super(ExceptionType.BAD_CREDENTIAL);
    }
}

package codesquad.bows.member.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class RoleNotExistsException extends BusinessException {
    public RoleNotExistsException(){
        super(ExceptionType.ROLE_NOT_EXISTS);
    }
}

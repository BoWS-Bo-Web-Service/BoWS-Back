package codesquad.bows.project.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class DuplicatedDomainException extends BusinessException {

    public DuplicatedDomainException(){
        super(ExceptionType.DUPLICATED_DOMAIN);
    }
}

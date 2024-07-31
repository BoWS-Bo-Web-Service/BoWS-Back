package codesquad.bows.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class CreationFailedException extends BusinessException {
    public CreationFailedException(){
        super(ExceptionType.PROJECT_CREATE_FAILED);
    }
}

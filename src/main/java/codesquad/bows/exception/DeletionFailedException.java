package codesquad.bows.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class DeletionFailedException extends BusinessException {
    public DeletionFailedException(){
        super(ExceptionType.PROJECT_DELETE_FAILED);
    }
}

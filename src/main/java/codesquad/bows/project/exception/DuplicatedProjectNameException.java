package codesquad.bows.project.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class DuplicatedProjectNameException extends BusinessException {

    public DuplicatedProjectNameException(){
        super(ExceptionType.DUPLICATED_PROJECT_NAME);
    }
}
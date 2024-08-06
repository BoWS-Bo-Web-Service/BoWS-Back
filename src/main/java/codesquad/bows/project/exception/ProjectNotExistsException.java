package codesquad.bows.project.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class ProjectNotExistsException extends BusinessException {
    public ProjectNotExistsException(){
        super(ExceptionType.PROJECT_NOT_EXIST);
    }
}

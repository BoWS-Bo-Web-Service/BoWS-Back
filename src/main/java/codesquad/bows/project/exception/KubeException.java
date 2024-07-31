package codesquad.bows.project.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class KubeException extends BusinessException {
    public KubeException() {
        super(ExceptionType.KUBECTL_EXECUTION_FAILED);
    }
}

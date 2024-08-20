package codesquad.bows.member.exception;

import codesquad.bows.global.exception.BusinessException;
import codesquad.bows.global.exception.ExceptionType;

public class InvitationCodeMismatchException extends BusinessException {
    public InvitationCodeMismatchException() {
        super(ExceptionType.INVITATION_CODE_MISMATCH);
    }
}

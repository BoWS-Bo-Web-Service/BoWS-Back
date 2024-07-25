package codesquad.bows.global.exception.response;

import codesquad.bows.global.exception.BusinessException;

public record BusinessExceptionResponse(String exceptionClass, String errorMessage) {

    public static BusinessExceptionResponse from(BusinessException e){
        String exceptionClass = e.getClass().getSimpleName();
        String errorMessage = e.getExceptionType().getMessage();

        return new BusinessExceptionResponse(exceptionClass, errorMessage);
    }

}

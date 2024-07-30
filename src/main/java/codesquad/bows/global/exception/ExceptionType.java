package codesquad.bows.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    // Application CRUD
    APPLICATION_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "애플리케이션 생성에 실패했습니다"),
    APPLICATION_DESTROY_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "애플리케이션 삭제에 실패했습니다"),

    // Kubernetes Client
    KUBECTL_EXECUTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5101, "쿠버네티스 제어 중 에러가 발생했습니다");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ExceptionType(HttpStatus httpStatus, Integer code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
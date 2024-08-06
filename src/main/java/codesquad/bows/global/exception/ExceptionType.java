package codesquad.bows.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    // PROJECT CRUD
    PROJECT_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "프로젝트 생성에 실패했습니다"),
    PROJECT_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "프로젝트 삭제에 실패했습니다"),
    DUPLICATED_DOMAIN(HttpStatus.BAD_REQUEST, 5003, "이미 사용중인 도메인은 입력 불가합니다"),

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
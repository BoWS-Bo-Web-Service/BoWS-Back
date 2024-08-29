package codesquad.bows.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    // User
    UNAUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, 4001, "인증되지 않은 사용자입니다"),
    INVITATION_CODE_MISMATCH(HttpStatus.BAD_REQUEST, 4002, "초대코드가 일치하지 않습니다."),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, 4003, "중복된 아이디를 입력했습니다."),
    ROLE_NOT_EXISTS(HttpStatus.INTERNAL_SERVER_ERROR, 4004, "회원가입이 실패했습니다. 관리자에게 문의해주세요"),

    // Refresh Token
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 4101, "리프레시 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 4102, "유효하지 않은 리프레시 토큰입니다."),

    // PROJECT CRUD
    PROJECT_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "프로젝트 생성에 실패했습니다"),
    PROJECT_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "프로젝트 삭제에 실패했습니다"),
    DUPLICATED_DOMAIN(HttpStatus.BAD_REQUEST, 5003, "이미 사용중인 도메인은 입력 불가합니다"),
    PROJECT_NOT_EXIST(HttpStatus.BAD_REQUEST, 5004, "존재하지 않는 프로젝트에 대한 접근입니다"),

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
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
    BAD_CREDENTIAL(HttpStatus.BAD_REQUEST, 4005, "아이디 혹은 비밀번호가 잘못되었습니다."),
    UNHANDLED_AUTHENTICATION_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 4099, "관리되지 않는 인증 예외입니다."),

    // Token
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 4101, "토큰이 만료되었습니다."),
    INVALID_TOKEN_SIGNATURE(HttpStatus.BAD_REQUEST, 4102, "토큰의 서명이 올바르지 않습니다."),
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, 4103, "토큰이 변형되었습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 4104, "유효하지 않은 토큰입니다."),
    UNHANDLED_TOKEN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 4199, "관리되지 않는 Jwt 예외입니다."),

    // PROJECT CRUD
    PROJECT_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "프로젝트 생성에 실패했습니다"),
    PROJECT_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "프로젝트 삭제에 실패했습니다"),
    DUPLICATED_DOMAIN(HttpStatus.BAD_REQUEST, 5003, "이미 사용중인 도메인은 입력 불가합니다"),
    PROJECT_NOT_EXIST(HttpStatus.NOT_FOUND, 5004, "존재하지 않는 프로젝트에 대한 접근입니다"),
    DUPLICATED_PROJECT_NAME(HttpStatus.BAD_REQUEST, 5005, "이미 사용중인 프로젝트 이름입니다"),

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
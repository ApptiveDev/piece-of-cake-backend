package apptive.com.common.auth.exception;

import apptive.com.common.base.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum AuthExceptionType implements BaseExceptionType {

    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "필수 요청 변수가 누락되었습니다."),

    // 401 Unauthorized
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    // 403 Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    AuthExceptionType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrMsg() {
        return this.errorMessage;
    }
}

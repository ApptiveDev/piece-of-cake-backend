package apptive.com.store.store.exception;

import apptive.com.common.base.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum StoreExceptionType implements BaseExceptionType {

    // 400 Bad Request
    ALREADY_EXIST_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    NOT_SAME_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 같지 않습니다."),

    // 404 Not found
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."),
    NOT_FOUND_OWNER(HttpStatus.NOT_FOUND, "사장님 정보를 찾을 수 없습니다."),

    // 417 Exception Failed
    SERVER_ERR(HttpStatus.EXPECTATION_FAILED, "저장에 실패하였습니다. 다시 시도해주세요.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    StoreExceptionType(HttpStatus httpStatus, String errorMessage) {
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

package apptive.com.store.cake.exception;

import apptive.com.common.base.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum CakeExceptionType implements BaseExceptionType {

    // 400 Bad Request
    WRONG_INPUT(HttpStatus.BAD_REQUEST, "가게ID와 케이크ID를 확인해주세요"),

    // 404 Not found
    NOT_FOUND_CAKE(HttpStatus.NOT_FOUND, "해당 케이크를 찾을 수 없습니다."),

    // 417 Exception Failed
    SERVER_ERR(HttpStatus.EXPECTATION_FAILED, "저장에 실패하였습니다. 다시 시도해주세요.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    CakeExceptionType(HttpStatus httpStatus, String errorMessage) {
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

package apptive.pieceOfCake.order.exception;

import apptive.pieceOfCake.base.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum OrderExceptionType implements BaseExceptionType {

    // 404 Not found
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "존재하지 않는 주문번호 입니다."),
    NOT_MATCH_MEMBER(HttpStatus.NOT_FOUND, "주문번호와 주문자가 일치하지 않습니다."),

    // 417 Exception Failed
    SERVER_ERR(HttpStatus.EXPECTATION_FAILED, "저장에 실패하였습니다. 다시 시도해주세요.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    OrderExceptionType(HttpStatus httpStatus, String errorMessage) {
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
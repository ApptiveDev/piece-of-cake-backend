package apptive.com.member.order.exception;

import apptive.com.common.base.exception.BaseException;
import apptive.com.common.base.exception.BaseExceptionType;

public class OrderException extends BaseException {

    private BaseExceptionType exType;

    public OrderException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}

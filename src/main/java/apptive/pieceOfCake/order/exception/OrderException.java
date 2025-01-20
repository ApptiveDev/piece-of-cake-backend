package apptive.pieceOfCake.order.exception;

import apptive.pieceOfCake.base.exception.BaseException;
import apptive.pieceOfCake.base.exception.BaseExceptionType;

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

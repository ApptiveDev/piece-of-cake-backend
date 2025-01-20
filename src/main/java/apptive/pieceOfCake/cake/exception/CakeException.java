package apptive.pieceOfCake.cake.exception;

import apptive.pieceOfCake.base.exception.BaseException;
import apptive.pieceOfCake.base.exception.BaseExceptionType;

public class CakeException extends BaseException {

    private BaseExceptionType exType;

    public CakeException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}

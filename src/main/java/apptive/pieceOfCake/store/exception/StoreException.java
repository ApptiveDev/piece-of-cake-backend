package apptive.pieceOfCake.store.exception;

import apptive.pieceOfCake.base.exception.BaseException;
import apptive.pieceOfCake.base.exception.BaseExceptionType;

public class StoreException extends BaseException {

    private BaseExceptionType exType;

    public StoreException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}

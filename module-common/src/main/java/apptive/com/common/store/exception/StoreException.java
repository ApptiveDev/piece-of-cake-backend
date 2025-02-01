package apptive.com.common.store.exception;

import apptive.com.common.base.exception.BaseException;
import apptive.com.common.base.exception.BaseExceptionType;

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

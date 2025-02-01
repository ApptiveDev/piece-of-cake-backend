package apptive.com.common.cake.exception;

import apptive.com.common.base.exception.BaseException;
import apptive.com.common.base.exception.BaseExceptionType;

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

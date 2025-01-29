package apptive.com.common.auth.exception;

import apptive.com.common.base.exception.BaseException;
import apptive.com.common.base.exception.BaseExceptionType;

public class AuthException extends BaseException {

    private BaseExceptionType exType;

    public AuthException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}

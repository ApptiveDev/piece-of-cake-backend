package apptive.pieceOfCake.users.exception;

import apptive.pieceOfCake.base.exception.BaseException;
import apptive.pieceOfCake.base.exception.BaseExceptionType;

public class UserException extends BaseException {

    private BaseExceptionType exType;

    public UserException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}

package apptive.pieceOfCake.users.exception;

import apptive.pieceOfCake.base.exception.BaseException;
import apptive.pieceOfCake.base.exception.BaseExceptionType;

public class MemberException extends BaseException {

    private BaseExceptionType exType;

    public MemberException(BaseExceptionType exType) {
        this.exType = exType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exType;
    }
}

package apptive.com.member.users.exception;

import apptive.com.common.base.exception.BaseException;
import apptive.com.common.base.exception.BaseExceptionType;

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

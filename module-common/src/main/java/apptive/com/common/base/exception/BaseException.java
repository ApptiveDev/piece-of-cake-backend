package apptive.com.common.base.exception;

public abstract class BaseException extends RuntimeException {

    public abstract BaseExceptionType getExceptionType();
}
package apptive.pieceOfCake.base.exception;

public abstract class BaseException extends RuntimeException {

    public abstract BaseExceptionType getExceptionType();
}
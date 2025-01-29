package apptive.com.common.base.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType{

    HttpStatus getHttpStatus();
    String getErrMsg();
}
package com.udyogg.services.common.exception;
/*
@author Ravi Singh
*/


import com.udyogg.services.common.error.ErrorCodes.ErrorCode;
import com.udyogg.services.common.logging.CustomLogger;
import lombok.Getter;

public final class CustomException extends RuntimeException {

    @Getter
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


    public static CustomException wrap(Throwable ex) {
        /* this will help in finding out errors which are not known.. like null pointer.. IO exception... which are not in ErrorCodes list*/
        if(!(ex instanceof CustomException)) {
            CustomLogger.error("Unknown Custom Error::"+ex.getMessage(), ex);
        }
        return ex instanceof CustomException ? (CustomException) ex : new CustomException(ErrorCode.INTERNAL_ERROR);
    }

}

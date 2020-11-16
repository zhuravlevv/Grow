package com.epam.service.exception;

public class GlobalServiceException extends RuntimeException {

    public GlobalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalServiceException(Throwable cause) {
        super(cause);
    }
}

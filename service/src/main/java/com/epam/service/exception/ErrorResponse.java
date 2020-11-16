package com.epam.service.exception;

public class ErrorResponse {

    private String message;
    private String debugMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

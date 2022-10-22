package com.elearning.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private final ErrorType errorType;

    public ResourceNotFoundException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ResourceNotFoundException(String message, Throwable cause, ErrorType errorType) {
        super(message, cause);
        this.errorType = errorType;
    }
}

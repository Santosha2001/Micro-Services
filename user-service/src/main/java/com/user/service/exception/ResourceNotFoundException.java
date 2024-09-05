package com.user.service.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("The requested resource was not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

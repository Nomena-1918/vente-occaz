package org.voiture.venteoccaz.exception;

public class AccessDeniedException extends Exception{
    private final String message;
    public AccessDeniedException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}

package ru.danilenkoya.linkShortener.exception;

public class NotFoundException extends LinkShortenerException{

    public NotFoundException(String message, Exception cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }
}

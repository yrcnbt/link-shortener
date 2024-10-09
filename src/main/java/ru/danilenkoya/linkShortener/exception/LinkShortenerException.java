package ru.danilenkoya.linkShortener.exception;

public class LinkShortenerException extends RuntimeException{
    public LinkShortenerException(String message, Exception cause) {
        super(message, cause);
    }

    public LinkShortenerException(String message) {
        super(message);
    }
}

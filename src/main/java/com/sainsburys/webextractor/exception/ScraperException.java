package com.sainsburys.webextractor.exception;

/**
 * Return custom exception.
 */
public class ScraperException extends Exception {
    private String message;

    public ScraperException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

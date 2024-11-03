package com.vwapcalculator.common;

/**
 * Exception thrown when a user session is inactive or has expired.
 */
public class SessionInactiveException extends RuntimeException {
    public SessionInactiveException(String message) {
        super(message);
    }
}
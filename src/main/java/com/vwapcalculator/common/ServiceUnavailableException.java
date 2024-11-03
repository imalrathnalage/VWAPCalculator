package com.vwapcalculator.common;

/**
 * Exception indicating that a service is unavailable.
 */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
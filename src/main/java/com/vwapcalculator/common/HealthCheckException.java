package com.vwapcalculator.common;

/**
 * Exception thrown when a health check fails.
 */
public class HealthCheckException extends RuntimeException {
    public HealthCheckException(String message) {
        super(message);
    }
}
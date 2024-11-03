package com.vwapcalculator.common;

/**
 * Exception thrown when requested data is not found.
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
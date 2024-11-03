package com.vwapcalculator.common;

/**
 * Represents an error response with a message, typically used for conveying error details to clients.
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
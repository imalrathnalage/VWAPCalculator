package com.vwapcalculator.controller;

import com.vwapcalculator.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MarketControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(MarketControllerAdvice.class);
    private static final String GENERIC_ERROR_MSG = "An unexpected error occurred.";

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(final DataNotFoundException exception) {
        logger.warn("Data not found.", exception);
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SessionInactiveException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleSessionInactiveException(final SessionInactiveException exception) {
        logger.warn("Session is inactive.", exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorResponse handleServiceUnavailableException(final ServiceUnavailableException exception) {
        logger.error("Service unavailable.", exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(HealthCheckException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleHealthCheckException(final HealthCheckException exception) {
        logger.error("Health check failed.", exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        StringBuilder errors = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
        );
        logger.error("Validation error: {}", errors.toString());
        return new ErrorResponse(errors.toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleGenericException(final Exception exception) {
        logger.error(GENERIC_ERROR_MSG, exception);
        return new ErrorResponse(GENERIC_ERROR_MSG);
    }
}
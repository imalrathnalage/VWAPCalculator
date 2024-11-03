package com.vwapcalculator;

import com.vwapcalculator.common.*;
import com.vwapcalculator.controller.MarketControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MarketControllerAdviceTest {

    private MarketControllerAdvice advice;

    @BeforeEach
    void setUp() {
        advice = new MarketControllerAdvice();
    }

    @Test
    void handleValidationException() {
        // Mock the BindingResult and MethodArgumentNotValidException
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);

        // Configure the BindingResult mock to return a FieldError list
        FieldError fieldError = new FieldError("objectName", "field", "Validation error");
        List<FieldError> errors = List.of(fieldError);
        when(bindingResult.getFieldErrors()).thenReturn(errors);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        // Execute the method and verify the response
        ErrorResponse response = advice.handleValidationException(exception);
        assertEquals("field: Validation error; ", response.getMessage());
    }

    @Test
    void handleSessionInactiveException() {
        SessionInactiveException exception = new SessionInactiveException("Session inactive");
        ErrorResponse response = advice.handleSessionInactiveException(exception);
        assertEquals("Session inactive", response.getMessage());
    }

    @Test
    void handleServiceUnavailableException() {
        ServiceUnavailableException exception = new ServiceUnavailableException("Service unavailable");
        ErrorResponse response = advice.handleServiceUnavailableException(exception);
        assertEquals("Service unavailable", response.getMessage());
    }

    @Test
    void handleHealthCheckException() {
        HealthCheckException exception = new HealthCheckException("Health check failed");
        ErrorResponse response = advice.handleHealthCheckException(exception);
        assertEquals("Health check failed", response.getMessage());
    }

    @Test
    void handleGenericException() {
        Exception exception = new Exception("Generic exception");
        ErrorResponse response = advice.handleGenericException(exception);
        assertEquals("An unexpected error occurred.", response.getMessage());
    }

    @Test
    void handleDataNotFoundException() {
        DataNotFoundException exception = new DataNotFoundException("Data not found");
        ResponseEntity<ErrorResponse> response = advice.handleDataNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Data not found", response.getBody().getMessage());
    }
}
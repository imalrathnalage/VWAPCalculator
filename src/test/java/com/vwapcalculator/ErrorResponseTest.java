package com.vwapcalculator;

import com.vwapcalculator.common.ErrorResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ErrorResponseTest {

    @Test
    void testDefaultConstructor() {
        ErrorResponse errorResponse = new ErrorResponse();
        assertNull(errorResponse.getMessage());  // Verify that message is null by default
    }

    @Test
    void testParameterizedConstructor() {
        ErrorResponse errorResponse = new ErrorResponse("Error occurred");
        assertEquals("Error occurred", errorResponse.getMessage());  // Verify message is set correctly
    }

    @Test
    void testSetMessage() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("New error message");
        assertEquals("New error message", errorResponse.getMessage());  // Verify message is updated
    }

    @Test
    void testGetMessage() {
        ErrorResponse errorResponse = new ErrorResponse("Initial error message");
        assertEquals("Initial error message", errorResponse.getMessage());  // Verify message retrieval
    }
}
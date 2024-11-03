package com.vwapcalculator;

import com.vwapcalculator.config.RequestResponseLoggingFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.mockito.Mockito.*;

class RequestResponseLoggingFilterTest {

    private RequestResponseLoggingFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setup() {
        filter = new RequestResponseLoggingFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);

        // Enable logging for tests
        ReflectionTestUtils.setField(filter, "loggingEnabled", true);
    }

    @Test
    void testDoFilterInternalWithLoggingEnabled() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/test-uri");
        when(request.getMethod()).thenReturn("GET");
        when(response.getStatus()).thenReturn(200);

        filter.doFilterInternal(request, response, filterChain);

        // Verify request and response logging
        verify(request).getRequestURI();
        verify(request).getMethod();
        verify(response).getStatus();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalWithLoggingDisabled() throws ServletException, IOException {
        // Disable logging
        ReflectionTestUtils.setField(filter, "loggingEnabled", false);

        filter.doFilterInternal(request, response, filterChain);

        // Verify that request and response details are not logged
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(request, response);
    }
}
package com.vwapcalculator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Value("${logging.request-response.enabled:false}")
    private boolean loggingEnabled;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {

        if (loggingEnabled) {
            logRequestDetails(request);
        }

        filterChain.doFilter(request, response);

        if (loggingEnabled) {
            logResponseDetails(response);
        }
    }

    private void logRequestDetails(HttpServletRequest request) {
        logger.info("========== Incoming Request ==========");
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Request Method: {}", request.getMethod());
        logger.info("Request Headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            Collections.list(headerNames).forEach(headerName ->
                    logger.info("  {}: {}", headerName, request.getHeader(headerName))
            );
        }
        logger.info("======================================");
    }

    private void logResponseDetails(HttpServletResponse response) {
        logger.info("========== Outgoing Response =========");
        logger.info("Response Status: {}", response.getStatus());
        logger.info("======================================");
    }
}
package com.vwapcalculator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

/**
 * Record representing price data for VWAP (Volume Weighted Average Price) calculation.
 * This record enforces validation on fields to ensure data accuracy.
 *
 * Fields:
 * - timestamp: The time when the data was recorded.
 * - currencyPair: The currency pair in "XXX/YYY" format (e.g., "AUD/USD").
 * - price: The price value for the given currency pair (must be positive).
 * - volume: The traded volume for the currency pair (must be positive).
 */
public record PriceData(
        @NotNull LocalDateTime timestamp,

        // Validates currency pair to ensure it's in the format 'XXX/YYY', where XXX and YYY are uppercase alphabets
        @NotNull
        @Pattern(regexp = "^[A-Z]{3}/[A-Z]{3}$", message = "Currency pair must match the format 'XXX/YYY'")
        String currencyPair,

        // Ensures that price is a positive double
        @Positive(message = "Price must be a positive number")
        double price,

        // Ensures that volume is a positive long integer
        @Positive(message = "Volume must be a positive number")
        long volume
) {}
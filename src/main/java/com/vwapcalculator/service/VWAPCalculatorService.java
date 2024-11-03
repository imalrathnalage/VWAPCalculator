package com.vwapcalculator.service;

import com.vwapcalculator.model.PriceData;

/**
 * VWAPCalculatorService provides methods to process price data and manage VWAP calculations
 * for various currency pairs.
 */
public interface VWAPCalculatorService {

    /**
     * Processes incoming price data and updates the VWAP calculation for the specified currency pair.
     *
     * @param data The PriceData containing timestamp, currency pair, price, and volume information.
     * @return The updated VWAP for the given currency pair.
     */
    double processPriceData(PriceData data);

    /**
     * Resets the VWAP calculation for a specified currency pair, clearing any stored data.
     *
     * @param currencyPair The currency pair identifier (e.g., "AUD/USD") to reset.
     */
    void reset(String currencyPair);
}
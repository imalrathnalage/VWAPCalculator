package com.vwapcalculator.service.impl;

import com.vwapcalculator.model.PriceData;
import com.vwapcalculator.model.VWAPData;
import com.vwapcalculator.service.VWAPCalculatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * VWAPCalculatorServiceImpl is a service implementation for calculating the Volume-Weighted Average Price (VWAP)
 * for various currency pairs within a specified time window.
 */
@Service
public class VWAPCalculatorServiceImpl implements VWAPCalculatorService {

    private static final Logger logger = LogManager.getLogger(VWAPCalculatorServiceImpl.class);
    private final Map<String, VWAPData> vwapDataMap = new ConcurrentHashMap<>();
    private final long timeWindowMinutes;

    /**
     * Constructor to initialize the VWAPCalculatorService with a specified time window.
     *
     * @param timeWindowMinutes The duration (in minutes) over which VWAP should be calculated.
     */
    public VWAPCalculatorServiceImpl(@Value("${vwap.time-window.minutes}") long timeWindowMinutes) {
        this.timeWindowMinutes = timeWindowMinutes;
    }

    /**
     * Processes a new price data entry for a currency pair and calculates an updated VWAP.
     *
     * @param data The incoming price data, including timestamp, currency pair, price, and volume.
     * @return The updated VWAP for the specified currency pair.
     * @throws IllegalArgumentException if the volume in the data is negative.
     */
    @Override
    public double processPriceData(PriceData data) {
        if (data.volume() < 0) {
            logger.error("Negative volume provided for currency pair {}: {}", data.currencyPair(), data.volume());
            throw new IllegalArgumentException("Volume cannot be negative");
        }

        // Get or create VWAPData for the currency pair
        VWAPData vwapData = vwapDataMap.computeIfAbsent(data.currencyPair(), k -> new VWAPData(timeWindowMinutes));

        // Synchronize to handle concurrency and update VWAP calculation
        synchronized (vwapData) {
            vwapData.addData(data);
            double vwap = vwapData.calculateVWAP();
            logger.info("Updated VWAP for {}: {}", data.currencyPair(), vwap);
            return vwap;
        }
    }

    /**
     * Resets the VWAP data for a specified currency pair.
     *
     * @param currencyPair The currency pair to reset (e.g., "AUD/USD").
     */
    @Override
    public void reset(String currencyPair) {
        VWAPData vwapData = vwapDataMap.get(currencyPair);
        if (vwapData != null) {
            synchronized (vwapData) {
                vwapData.reset();
                logger.info("Reset VWAP data for {}", currencyPair);
            }
        }
    }
}
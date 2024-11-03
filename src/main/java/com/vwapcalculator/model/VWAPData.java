package com.vwapcalculator.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Deque;
import java.util.LinkedList;

/**
 * VWAPData manages a time-windowed calculation of VWAP (Volume Weighted Average Price)
 * for a specific currency pair.
 *
 * This class maintains a moving window of price data, calculates the VWAP for that window,
 * and ensures thread-safety for concurrent updates.
 */
public class VWAPData {
    private final Deque<PriceData> dataWindow = new LinkedList<>();  // Stores data within the time window
    private double cumulativePriceVolume = 0.0;                      // Cumulative sum of price * volume
    private long cumulativeVolume = 0;                               // Cumulative volume over the time window
    private final long timeWindowMinutes;                            // Duration of the moving time window in minutes

    /**
     * Constructs a VWAPData object for a given time window.
     *
     * @param timeWindowMinutes The duration in minutes for the VWAP calculation window
     */
    public VWAPData(long timeWindowMinutes) {
        this.timeWindowMinutes = timeWindowMinutes;
    }

    /**
     * Adds new price data to the time window and removes outdated entries.
     *
     * @param data The PriceData instance containing timestamp, price, and volume.
     */
    public synchronized void addData(PriceData data) {
        LocalDateTime cutoff = data.timestamp().minus(timeWindowMinutes, ChronoUnit.MINUTES);

        // Remove outdated data that falls outside the time window
        while (!dataWindow.isEmpty() && dataWindow.peekFirst().timestamp().isBefore(cutoff)) {
            PriceData outdatedData = dataWindow.pollFirst();
            cumulativePriceVolume -= outdatedData.price() * outdatedData.volume();
            cumulativeVolume -= outdatedData.volume();
        }

        // Add new data to the window and update cumulative calculations
        dataWindow.addLast(data);
        cumulativePriceVolume += data.price() * data.volume();
        cumulativeVolume += data.volume();
    }

    /**
     * Calculates the VWAP for the current data in the time window.
     *
     * @return The calculated VWAP, or 0.0 if no data is available.
     */
    public synchronized double calculateVWAP() {
        return cumulativeVolume == 0 ? 0.0 : cumulativePriceVolume / cumulativeVolume;
    }

    /**
     * Resets the VWAP data by clearing the time window and cumulative calculations.
     */
    public synchronized void reset() {
        dataWindow.clear();
        cumulativePriceVolume = 0.0;
        cumulativeVolume = 0;
    }
}
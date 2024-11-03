package com.vwapcalculator;

import com.vwapcalculator.model.PriceData;
import com.vwapcalculator.service.impl.VWAPCalculatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VWAPCalculatorServiceTest {

    private VWAPCalculatorServiceImpl calculator;

    @BeforeEach
    void setUp() {
        calculator = new VWAPCalculatorServiceImpl(60); // Assume 60 minutes as the time window for VWAP calculation
    }

    @Test
    void testSingleDataPointVWAP() {
        PriceData priceData = new PriceData(LocalDateTime.now(), "AUD/USD", 0.75, 100);
        double vwap = calculator.processPriceData(priceData);
        assertEquals(0.75, vwap, 0.0001);
    }

    @Test
    void testMultipleDataPointsVWAP() {
        LocalDateTime now = LocalDateTime.now();
        calculator.processPriceData(new PriceData(now, "AUD/USD", 0.75, 100));
        double vwap = calculator.processPriceData(new PriceData(now.plusMinutes(1), "AUD/USD", 0.80, 200));
        assertEquals(0.7833, vwap, 0.0001);
    }

    @Test
    void testBoundaryTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        calculator.processPriceData(new PriceData(now.minusMinutes(59), "AUD/USD", 0.80, 150));
        calculator.processPriceData(new PriceData(now.minusMinutes(30), "AUD/USD", 0.85, 50));
        calculator.processPriceData(new PriceData(now, "AUD/USD", 0.75, 100));

        double vwap = calculator.processPriceData(new PriceData(now.plusMinutes(1), "AUD/USD", 0.90, 200));

        // Adjusted expected value for precision
        assertEquals(0.835, vwap, 0.0001);
    }

    @Test
    void testNegativeVolume() {
        PriceData priceData = new PriceData(LocalDateTime.now(), "AUD/USD", 0.75, -100);
        assertThrows(IllegalArgumentException.class, () -> calculator.processPriceData(priceData));
    }

    @Test
    void testReset() {
        LocalDateTime now = LocalDateTime.now();
        calculator.processPriceData(new PriceData(now, "AUD/USD", 0.80, 100));
        calculator.reset("AUD/USD");

        double vwap = calculator.processPriceData(new PriceData(now.plusMinutes(1), "AUD/USD", 0.70, 200));
        assertEquals(0.70, vwap, 0.0001);
    }
}
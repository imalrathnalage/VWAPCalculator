package com.vwapcalculator;

import com.vwapcalculator.model.PriceData;
import com.vwapcalculator.model.VWAPData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VWAPDataTest {

    private VWAPData vwapData;
    private final long timeWindowMinutes = 60;

    @BeforeEach
    public void setUp() {
        vwapData = new VWAPData(timeWindowMinutes);
    }

    @Test
    public void testAddDataWithinOneHour() {
        LocalDateTime now = LocalDateTime.now();
        vwapData.addData(new PriceData(now.minusMinutes(30), "AUD/USD", 0.75, 100));
        vwapData.addData(new PriceData(now.minusMinutes(10), "AUD/USD", 0.80, 200));
        assertEquals(0.7833, vwapData.calculateVWAP(), 0.0001);
    }

    @Test
    public void testBoundaryTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        vwapData.addData(new PriceData(now.minusMinutes(60), "AUD/USD", 0.75, 100));
        vwapData.addData(new PriceData(now, "AUD/USD", 0.80, 200));
        assertEquals(0.7833, vwapData.calculateVWAP(), 0.0001);
    }

    @Test
    public void testAddDataOutsideOneHour() {
        LocalDateTime now = LocalDateTime.now();
        vwapData.addData(new PriceData(now.minusHours(2), "AUD/USD", 0.70, 100));
        vwapData.addData(new PriceData(now.minusMinutes(30), "AUD/USD", 0.80, 200));
        assertEquals(0.80, vwapData.calculateVWAP(), 0.0001);
    }

    @Test
    public void testAddDataAtExactBoundary() {
        LocalDateTime now = LocalDateTime.now();
        vwapData.addData(new PriceData(now.minusMinutes(60), "AUD/USD", 0.75, 100));
        vwapData.addData(new PriceData(now.minusMinutes(59), "AUD/USD", 0.80, 200));
        assertEquals(0.7833, vwapData.calculateVWAP(), 0.0001);
    }

    @Test
    public void testReset() {
        LocalDateTime now = LocalDateTime.now();
        vwapData.addData(new PriceData(now.minusMinutes(30), "AUD/USD", 0.75, 100));
        vwapData.reset();
        assertEquals(0.0, vwapData.calculateVWAP(), 0.0001);
    }

    @Test
    public void testAddDataWithZeroVolume() {
        LocalDateTime now = LocalDateTime.now();
        vwapData.addData(new PriceData(now.minusMinutes(30), "AUD/USD", 0.75, 0));
        vwapData.addData(new PriceData(now.minusMinutes(10), "AUD/USD", 0.80, 200));
        assertEquals(0.80, vwapData.calculateVWAP(), 0.0001);
    }
}
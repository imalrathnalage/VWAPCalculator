package com.vwapcalculator.controller;

import com.vwapcalculator.model.PriceData;
import com.vwapcalculator.service.VWAPCalculatorService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling VWAP (Volume Weighted Average Price) calculations.
 * This controller provides an endpoint to calculate the VWAP for a given PriceData object.
 */
@RestController
@RequestMapping("/api/vwap")
public class VWAPController {
    private static final Logger logger = LogManager.getLogger(VWAPController.class);
    private final VWAPCalculatorService vwapCalculatorService;

    /**
     * Constructor injection for the VWAPCalculatorService dependency.
     *
     * @param vwapCalculatorService The service that performs VWAP calculations.
     */
    @Autowired
    public VWAPController(VWAPCalculatorService vwapCalculatorService) {
        this.vwapCalculatorService = vwapCalculatorService;
    }

    /**
     * Endpoint to calculate the VWAP for a given PriceData object.
     * Accepts POST requests with a JSON body containing the price data.
     *
     * @param priceData The price data for which VWAP should be calculated.
     * @return ResponseEntity containing the calculated VWAP as a double.
     */
    @PostMapping("/calculate")
    public ResponseEntity<Double> calculateVWAP(@Valid @RequestBody PriceData priceData) {
        logger.info("Calculating VWAP for data: {}", priceData);

        // Delegate VWAP calculation to the service layer.
        double vwap = vwapCalculatorService.processPriceData(priceData);

        return ResponseEntity.ok(vwap);
    }
}
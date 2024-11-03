package com.vwapcalculator;

import com.vwapcalculator.config.AuthenticationFilter;
import com.vwapcalculator.controller.VWAPController;
import com.vwapcalculator.model.PriceData;
import com.vwapcalculator.service.VWAPCalculatorService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VWAPController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthenticationFilter.class))
@ContextConfiguration(classes = {VWAPController.class, VWAPCalculatorService.class})
class VWAPControllerTest {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private VWAPCalculatorService vwapCalculatorService;

    @Test
    void calculateVWAP_withInvalidData_shouldReturnBadRequest() throws Exception {
        String invalidPriceDataJson = """
                {
                    "timestamp": "2024-11-03T12:00:00",
                    "currencyPair": "AUDUSD", 
                    "price": -0.5,
                    "volume": -100
                }
                """;

        mockMvc.perform(post("/api/vwap/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPriceDataJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateVWAP_withValidData_shouldReturnVWAP() throws Exception {
        String validPriceDataJson = """
                {
                    "timestamp": "2024-11-03T12:00:00",
                    "currencyPair": "AUD/USD",
                    "price": 0.75,
                    "volume": 100
                }
                """;

        when(vwapCalculatorService.processPriceData(any(PriceData.class))).thenReturn(0.75);

        mockMvc.perform(post("/api/vwap/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validPriceDataJson))
                .andExpect(status().isOk());
    }
}

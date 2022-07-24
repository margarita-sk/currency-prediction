package com.mskokava.currencyprediction.controller;

import com.mskokava.currencyprediction.service.PredictionService;
import com.mskokava.currencyprediction.service.dto.PricesPrediction;
import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import javax.annotation.PostConstruct;

import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.ONE;
import static java.time.LocalDate.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PredictionController.class)
class PredictionControllerTest {

    private static final String GET_PREDICTION_URL = "/public/v1/prediction";

    @MockBean
    private PredictionService predictionService;

    @Autowired
    private PredictionController target;

    private WebTestClient webTestClient;

    @PostConstruct
    void setup() {
        this.webTestClient =
                MockMvcWebTestClient.bindToController(target).build();
    }

    @Test
    void shouldReturnOk_whenReceivePrediction_givenCoinAndAnalyzedPeriodAndPredictedPeriod() {
        // arrange
        final var prediction = new PricesPrediction(CryptoCoin.BTC, Map.of(now(), ONE));
        when(predictionService.receivePricesPrediction(any(), any(), anyInt())).thenReturn(prediction);

        // act
        final var response =
                webTestClient
                        .get()
                        .uri(uri ->
                                uri.path(GET_PREDICTION_URL)
                                        .queryParam("coin", "bitcoin")
                                        .queryParam("analyzedPeriod", "1w")
                                        .queryParam("predictedPeriod", "12")
                                        .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange();

        // assert
        response.expectStatus().isOk();
        response.expectBody(PricesPrediction.class).equals(prediction);
    }
}
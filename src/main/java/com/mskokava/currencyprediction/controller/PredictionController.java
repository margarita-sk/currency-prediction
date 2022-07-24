package com.mskokava.currencyprediction.controller;

import com.mskokava.currencyprediction.controller.dto.PredictionRequestDto;
import com.mskokava.currencyprediction.service.PredictionService;
import com.mskokava.currencyprediction.service.dto.PricesPrediction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/v1")
public class PredictionController {

    private final PredictionService predictionService;

    @GetMapping("/prediction")
    public ResponseEntity<PricesPrediction> receivePrediction(@Valid PredictionRequestDto dto) {
        final var prediction = predictionService.receivePricesPrediction(dto.getCoin(), dto.getAnalyzedPeriod(),
                dto.getPredictedPeriod());
        return new ResponseEntity<>(prediction, HttpStatus.OK);
    }
}

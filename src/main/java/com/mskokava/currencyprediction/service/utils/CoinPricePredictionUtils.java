package com.mskokava.currencyprediction.service.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import static java.math.RoundingMode.HALF_UP;

@Slf4j
@UtilityClass
public class CoinPricePredictionUtils {

    public void addPredictions(Map<LocalDate, BigDecimal> predictedPrices, BigDecimal actualPrice,
                               LocalDate actualDate, List<BigDecimal> historyPrices, int daysForPrediction) {
        if (daysForPrediction >= 1) {
            final var predictedPrice = predictPrice(historyPrices, actualPrice);
            actualDate = actualDate.plusDays(1);
            predictedPrices.put(actualDate, predictedPrice);
            historyPrices.add(predictedPrice);
            addPredictions(predictedPrices, predictedPrice, actualDate, historyPrices, daysForPrediction - 1);
        }
    }

    private BigDecimal predictPrice(List<BigDecimal> prices, BigDecimal lastPrice) {
        // price = (x2/x1 + x3/x2 + ... xi+1/xi) / i * xi
        final var coefficient = calculateChangingCoefficient(prices)
                .orElseThrow(() -> new RuntimeException("Can't calculate changing coefficient"));
        log.debug("Changing coefficient is calculated: {}", coefficient);
        return BigDecimal.valueOf(coefficient).multiply(lastPrice).setScale(lastPrice.scale(), HALF_UP);
    }

    private OptionalDouble calculateChangingCoefficient(List<BigDecimal> prices) {
        final var dividable = new ArrayList<>(prices);
        dividable.remove(0);
        final var divisors = new ArrayList<>(prices);
        divisors.remove(prices.size() - 1);
        return dividable.stream().map(element -> {
            final var index = dividable.indexOf(element);
            return element.divide(divisors.get(index), element.scale(), HALF_UP);
        }).mapToDouble(BigDecimal::doubleValue).average();
    }
}

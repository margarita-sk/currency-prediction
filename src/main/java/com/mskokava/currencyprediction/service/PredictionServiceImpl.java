package com.mskokava.currencyprediction.service;

import com.mskokava.currencyprediction.client.CurrencyClient;
import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import com.mskokava.currencyprediction.service.enums.Currency;
import com.mskokava.currencyprediction.service.enums.TimePeriod;
import com.mskokava.currencyprediction.service.dto.PricesPrediction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.mskokava.currencyprediction.service.utils.CoinPricePredictionUtils.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private final CurrencyClient currencyClient;

    @Override
    public PricesPrediction receivePricesPrediction(CryptoCoin coin, TimePeriod analysingPeriod, Integer daysForPrediction) {
        final var actualCoinPrice = currencyClient.receiveActualCoinPrice(coin, Currency.USD);
        final var historyPrices = currencyClient.receiveHistoryCoinPrices(analysingPeriod, coin);

        final var pricePredictions = new HashMap<LocalDate, BigDecimal>();
        final var now = LocalDate.now();
        pricePredictions.put(now, actualCoinPrice);
        log.warn("You are using inefficient formula for prices prediction!");
        addPredictions(pricePredictions, actualCoinPrice, now, historyPrices.getPrices(), daysForPrediction);

        return new PricesPrediction(coin, pricePredictions);
    }

}

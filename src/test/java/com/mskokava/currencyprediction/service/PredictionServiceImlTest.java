package com.mskokava.currencyprediction.service;

import com.mskokava.currencyprediction.client.CurrencyClient;
import com.mskokava.currencyprediction.service.dto.PricesByCurrencyDto;
import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import com.mskokava.currencyprediction.service.enums.Currency;
import com.mskokava.currencyprediction.service.enums.TimePeriod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PredictionServiceImlTest {

    @Mock
    private CurrencyClient currencyClient;

    @InjectMocks
    private PredictionServiceImpl target;

    @Test
    void shouldReturnPricesPrediction_whenReceivePricesPrediction_givenCryptoCoinAndTimePeriodAndDaysForPrediction() {
        // arrange
        final var currentPrice = new BigDecimal("22554.4995317753");
        final var price0 = new BigDecimal("22555.22");
        final var price1 = new BigDecimal("22533.224434333");
        final var price2 = new BigDecimal("22551.00");
        final var pricesDto = new PricesByCurrencyDto();
        final var mutableListOfPrices = Stream.of(price0, price1, price2).collect(toList());
        pricesDto.setPrices(mutableListOfPrices);
        when(currencyClient.receiveActualCoinPrice(CryptoCoin.BTC, Currency.USD)).thenReturn(currentPrice);
        when(currencyClient.receiveHistoryCoinPrices(TimePeriod.WEEK, CryptoCoin.BTC)).thenReturn(pricesDto);

        // act
        final var actual = target.receivePricesPrediction(CryptoCoin.BTC, TimePeriod.WEEK, 3);

        // assert
        assertEquals(4, actual.getTimePrices().keySet().size());
    }


}
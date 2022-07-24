package com.mskokava.currencyprediction.client;

import com.mskokava.currencyprediction.client.dto.CoinDto;
import com.mskokava.currencyprediction.client.dto.HistoryPricesDto;
import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import com.mskokava.currencyprediction.service.enums.Currency;
import com.mskokava.currencyprediction.config.ModelMapperConfig;
import com.mskokava.currencyprediction.service.enums.TimePeriod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:test-application.properties")
@ContextConfiguration(classes = {CurrencyCoinStatsClient.class, ModelMapperConfig.class})
class CurrencyCoinStatsClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyCoinStatsClient target;


    @Test
    void shouldReturnBigDecimal_whenReceiveActualCoinPrice_givenCryptoCoinAndCurrency() {
        // arrange
        final var coinDTO = new CoinDto();
        final var coinPrice = new CoinDto.CoinPrice();
        coinPrice.setPrice(BigDecimal.ONE);
        coinDTO.setCoin(coinPrice);
        final var responseEntity = new ResponseEntity<>(coinDTO, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), same(CoinDto.class))).thenReturn(responseEntity);

        // act
        final var actual = target.receiveActualCoinPrice(CryptoCoin.BTC, Currency.USD);

        // assert
        assertEquals(coinPrice.getPrice(), actual);
    }

    @Test
    void shouldThrowException_whenReceiveActualCoinPrice_givenCryptoCoinAndCurrency() {
        // arrange
        final var responseEntity = new ResponseEntity<CoinDto>(HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), same(CoinDto.class))).thenReturn(responseEntity);

        // act - assert
        assertThrows(RuntimeException.class, () -> target.receiveActualCoinPrice(CryptoCoin.BTC, Currency.USD));
    }

    @Test
    void shouldReturnPricesByCurrencyDto_whenReceiveHistoryCoinPrices_givenTimePeriodAndCryptoCoin() {
        // arrange
        final var historyPrices = new HistoryPricesDto();
        historyPrices.setChart(List.of(List.of(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN)));
        final var response = new ResponseEntity<>(historyPrices, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), same(HistoryPricesDto.class))).thenReturn(response);

        // act
        final var actual = target.receiveHistoryCoinPrices(TimePeriod.WEEK, CryptoCoin.BTC);

        // assert
        assertEquals(Currency.USD, actual.getCurrency());
        assertEquals(List.of(BigDecimal.TEN), actual.getPrices());
    }

    @Test
    void shouldThrowException_whenReceiveHistoryCoinPrices_givenTimePeriodAndCryptoCoin() {
        // arrange
        final var response = new ResponseEntity<HistoryPricesDto>(HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), same(HistoryPricesDto.class))).thenReturn(response);


        // act - assert
        assertThrows(RuntimeException.class, () -> target.receiveHistoryCoinPrices(TimePeriod.WEEK, CryptoCoin.BTC));
    }
}
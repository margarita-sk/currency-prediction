package com.mskokava.currencyprediction.client;

import com.mskokava.currencyprediction.client.dto.*;
import com.mskokava.currencyprediction.service.dto.PricesByCurrencyDto;
import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import com.mskokava.currencyprediction.service.enums.Currency;
import com.mskokava.currencyprediction.service.enums.TimePeriod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
public class CurrencyCoinStatsClient implements CurrencyClient {

    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final String historyPricesUrl;
    private final String coinPriceUrl;
    private final String defaultCurrencyOfResource;

    public CurrencyCoinStatsClient(RestTemplate restTemplate,
                                   ModelMapper modelMapper,
                                   @Value("${coinstats.history.prices.url}") String historyPricesUrl,
                                   @Value("${coinstats.coin.price.url}") String coinPriceUrl,
                                   @Value("${coinstats.default.currency}") String defaultCurrencyOfResource) {
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
        this.historyPricesUrl = historyPricesUrl;
        this.coinPriceUrl = coinPriceUrl;
        this.defaultCurrencyOfResource = defaultCurrencyOfResource;
    }

    @Override
    public PricesByCurrencyDto receiveHistoryCoinPrices(TimePeriod period, CryptoCoin coin) {
        final var url = UriComponentsBuilder.fromUriString(historyPricesUrl)
                .queryParam("period", period.getPeriodId())
                .queryParam("coinId", coin.getCoinId())
                .toUriString();

        final var priceHistorySlice = restTemplate.getForEntity(url, HistoryPricesDto.class);
        if (!priceHistorySlice.hasBody()) {
            throw new RuntimeException("Can't receive history coin prices");
        }
        final var prices = modelMapper.map(priceHistorySlice.getBody(), PricesByCurrencyDto.class);
        prices.setCurrency(Currency.valueOf(defaultCurrencyOfResource));
        return prices;
    }

    @Override
    public BigDecimal receiveActualCoinPrice(CryptoCoin coin, Currency currency) {
        final var url = UriComponentsBuilder.fromUriString(coinPriceUrl + coin.getCoinId())
                .queryParam("currency", currency.name())
                .toUriString();

        final var priceResponse = restTemplate.getForEntity(url, CoinDto.class);
        if (!priceResponse.hasBody()) {
            throw new RuntimeException("Can't receive actual coin price");
        }
        return priceResponse.getBody().getCoin().getPrice();
    }
}

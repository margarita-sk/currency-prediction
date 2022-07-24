package com.mskokava.currencyprediction.config;

import com.mskokava.currencyprediction.client.dto.HistoryPricesDto;
import com.mskokava.currencyprediction.service.dto.PricesByCurrencyDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.stream.Collectors.toList;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        final var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(HistoryPricesDto.class, PricesByCurrencyDto.class)
                .setPostConverter(this::priceHistorySliceToPricesInUSDdto);

        return modelMapper;
    }

    private PricesByCurrencyDto priceHistorySliceToPricesInUSDdto(MappingContext<HistoryPricesDto,
            PricesByCurrencyDto> context) {
        final var priceHistorySlice = context.getSource();
        final var prices = priceHistorySlice.getChart()
                .stream()
                .map(pricesByCurrency -> pricesByCurrency.get(1))
                .collect(toList());
        final var pricesInUSD = context.getDestination();
        pricesInUSD.setPrices(prices);
        return pricesInUSD;
    }

}

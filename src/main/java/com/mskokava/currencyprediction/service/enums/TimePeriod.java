package com.mskokava.currencyprediction.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TimePeriod {

    WEEK("1w"), MONTH("1m"), YEAR("1y");

    private final String periodId;

    public static TimePeriod getByPeriodId(String periodId) {
        return Arrays.stream(values())
                .filter(period -> period.getPeriodId().equals(periodId))
                .findFirst()
                .orElseThrow();
    }
}

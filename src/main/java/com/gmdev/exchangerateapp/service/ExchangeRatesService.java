package com.gmdev.exchangerateapp.service;

import com.gmdev.exchangerateapp.model.CurrencyPair;
import com.gmdev.exchangerateapp.model.ExchangeRate;
import com.gmdev.exchangerateapp.repository.CurrencyPairRepository;
import com.gmdev.exchangerateapp.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final CBRParser cbrParser;
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyPairRepository currencyPairRepository;

    @Transactional(readOnly = true)
    public Float getRateByCurrencyPairIdAndDate( int currencyPairId, LocalDate date) {
        CurrencyPair currencyPair = currencyPairRepository.findById(currencyPairId).orElse(null);

        LocalDateTime dateTimeFrom = LocalDateTime.of(date, LocalTime.of(0,0,0));
        LocalDateTime dateTimeTo = LocalDateTime.of(date, LocalTime.of(23,59,59));

        Optional<ExchangeRate> optionalRate = exchangeRateRepository
                .findByCurrencyPairAndAndRateDateBetween(currencyPair, dateTimeFrom, dateTimeTo);

        if (optionalRate.isPresent()) {
            return optionalRate.get().getRateValue();
        } else return null;
    }

    @Transactional(readOnly = true)
    public Float getRateByCurrencyPairId ( int currencyPairId ) {
        CurrencyPair currencyPair = currencyPairRepository
                .findById(currencyPairId)
                .orElseThrow(() -> new IllegalArgumentException("No such currency_pair id"));
//
        Optional<ExchangeRate> optionalRate = exchangeRateRepository
                .findFirstByCurrencyPairOrderByRateDateDesc(currencyPair);
        if (optionalRate.isPresent()) {
            return optionalRate.get().getRateValue();
        } else return null;
    }
}

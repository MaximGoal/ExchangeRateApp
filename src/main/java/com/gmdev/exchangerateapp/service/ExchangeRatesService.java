package com.gmdev.exchangerateapp.service;

import com.gmdev.exchangerateapp.model.CurrencyPair;
import com.gmdev.exchangerateapp.model.ExchangeRate;
import com.gmdev.exchangerateapp.repository.CurrencyPairRepository;
import com.gmdev.exchangerateapp.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final CBRParser cbrParser;
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyPairRepository currencyPairRepository;

    @Transactional(readOnly = true)
    public Float getRateByCurrencyPairIdAndDate( int currencyPairId, LocalDate date) {
       date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Optional<ExchangeRate> optionalRate = exchangeRateRepository.findByCurrencyPairAndAndRateDate(currencyPairId, date);
        if (optionalRate.isPresent()) {
            return optionalRate.get().getRateValue();
        } else return null;
    }

    @Transactional(readOnly = true)
    public Float getRateByCurrencyPairId( int currencyPairId ) {
//        CurrencyPair currencyPair = currencyPairRepository
//                .findById(currencyPairId)
//                .orElseThrow(() -> new IllegalArgumentException("No such currency_pair id"));
//
        Optional<ExchangeRate> optionalRate = exchangeRateRepository
                .findFirstByCurrencyPairOrderByRateDateDesc(currencyPairId);
        if (optionalRate.isPresent()) {
            return optionalRate.get().getRateValue();
        } else return null;
    }
}

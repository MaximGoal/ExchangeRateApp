package com.gmdev.exchangerateapp.config;

import com.gmdev.exchangerateapp.repository.CurrencyPairRepository;
import com.gmdev.exchangerateapp.repository.ExchangeRateRepository;
import com.gmdev.exchangerateapp.service.CBRParser;
import com.gmdev.exchangerateapp.service.ExchangeRatesUpdateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

//    private ExchangeRateRepository exchangeRateRepository;
//    private CurrencyPairRepository currencyPairRepository;

//    @Bean
//    public CBRParser cbrParser () {
////        return new CBRParser(exchangeRateRepository, currencyPairRepository);
//        return CBRParser.get();
//    }

//    @Bean
//    public ExchangeRatesUpdateService exchangeRatesUpdateService () {
//        return new ExchangeRatesUpdateService();
//    }
}

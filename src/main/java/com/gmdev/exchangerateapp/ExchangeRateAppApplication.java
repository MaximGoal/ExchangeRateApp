package com.gmdev.exchangerateapp;

import com.gmdev.exchangerateapp.service.ExchangeRatesUpdateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExchangeRateAppApplication {

    private static ExchangeRatesUpdateService exchangeRatesUpdateService;

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateAppApplication.class, args);

        Thread thread = new Thread(exchangeRatesUpdateService.updateExchangeRatesTask());
        thread.start();
    }
}

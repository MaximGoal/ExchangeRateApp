package com.gmdev.exchangerateapp;

import com.gmdev.exchangerateapp.service.CBRParser;
import com.gmdev.exchangerateapp.service.ExchangeRatesUpdateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangeRateAppApplication {
    // TODO: @Transaction volatile indexes

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateAppApplication.class, args);
    }
}

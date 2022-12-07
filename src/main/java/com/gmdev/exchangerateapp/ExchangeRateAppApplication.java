package com.gmdev.exchangerateapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangeRateAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateAppApplication.class, args);
    }

}

package com.gmdev.exchangerateapp.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExchangeRatesUpdateService {

    private static CBRParser cbrParser = CBRParser.get();
    private static Logger logger = LoggerFactory.getLogger(ExchangeRatesUpdateService.class);

    @Scheduled(fixedRate = 60000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Start scheduleTaskWithFixedRate() in Thread: " + Thread.currentThread().getName());

        cbrParser.parsePage( cbrParser.getTodayPage() );
        cbrParser.updateExchangeRates();
    }

    public Runnable updateExchangeRatesTask() {
        return () -> {
            while (true) {
                try {

                    cbrParser.parsePage( cbrParser.getTodayPage() );
                    cbrParser.updateExchangeRates();

                    System.out.println("*** *** *** *** *** *** *** *** *** *** *** *** *** *** ***");

                    Thread.sleep(1000 * 60 * 5);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

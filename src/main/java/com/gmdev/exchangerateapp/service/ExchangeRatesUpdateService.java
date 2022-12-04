package com.gmdev.exchangerateapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExchangeRatesUpdateService {

    private static CBRParser cbrParser = CBRParser.get();

    public static void main(String[] args) {
//        ExchangeRatesUpdateService service = new ExchangeRatesUpdateService();
//        Thread thread = new Thread(service.updateExchangeRatesTask());
//        thread.start();
    }

    public Runnable updateExchangeRatesTask() {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return () -> {
            while (true) {
                try {

                    cbrParser.parsePage( cbrParser.getTodayPage() );
                    cbrParser.updateExchangeRates();

                    System.out.println("*** *** *** *** *** *** *** *** *** *** *** *** *** *** ***");

//                    String newDate = LocalDate.now().minusDays(1).toString();
//                    System.out.println("Date: " + newDate);
//                    CBRParser.parsePage(CBRParser.getPage(newDate));

//                    String command = reader.readLine();
//                    if (command.equals("stop")) break;
                    Thread.sleep(1000 * 60 * 5);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

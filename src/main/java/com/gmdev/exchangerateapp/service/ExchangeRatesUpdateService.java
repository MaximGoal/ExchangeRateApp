package com.gmdev.exchangerateapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExchangeRatesUpdateService {

    private static CBRParser cbrParser = CBRParser.get();

    public static void main(String[] args) {
        ExchangeRatesUpdateService service = new ExchangeRatesUpdateService();
        Thread thread = new Thread(service.updateExchangeRatesTask());
        thread.start();
    }

    private Runnable updateExchangeRatesTask() {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return () -> {
            while (true) {
                try {

                    CBRParser.parsePage( CBRParser.getTodayPage() );
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

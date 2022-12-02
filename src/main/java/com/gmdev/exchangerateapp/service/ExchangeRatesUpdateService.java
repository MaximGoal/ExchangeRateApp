package com.gmdev.exchangerateapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExchangeRatesUpdateService {

    public static void main(String[] args) {
        Thread thread = new Thread(updateExchangeRatesTask());
        thread.start();
    }

    private static Runnable updateExchangeRatesTask() {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return () -> {
            while (true) {
                try {

                    CBRParser.parsePage( CBRParser.getTodayPage() );

                    System.out.println("*** *** *** *** *** *** *** *** *** *** *** *** *** *** ***");
                    String newDate = LocalDate.now().minusDays(1).toString();
                    System.out.println("Date: " + newDate);
                    CBRParser.parsePage(CBRParser.getPage(newDate));

//                    String command = reader.readLine();
//                    if (command.equals("stop")) break;
                    Thread.sleep(1000 * 60 * 5);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

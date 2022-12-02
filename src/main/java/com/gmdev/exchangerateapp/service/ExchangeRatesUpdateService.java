package com.gmdev.exchangerateapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ExchangeRatesUpdateService {

//    private static CBRParser cbrParser;

    public static void main(String[] args) {
        Thread thread = new Thread(updateExchangeRates());
        thread.start();
    }

    private static Runnable updateExchangeRates() {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return () -> {
            while (true) {
                try {

                    CBRParser.parsePage( CBRParser.getTodayPage() );

                    System.out.println("*** *** *** *** *** *** *** *** *** *** *** *** *** *** ***");
                    String newDate = "21.11.2022";
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

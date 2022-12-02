package com.gmdev.exchangerateapp.service;

import com.gmdev.exchangerateapp.dto.CurrencyDataGetDto;
import com.gmdev.exchangerateapp.model.CurrencyPair;
import com.gmdev.exchangerateapp.model.ExchangeRate;
import com.gmdev.exchangerateapp.repository.CurrencyPairRepository;
import com.gmdev.exchangerateapp.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CBRParser {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyPairRepository currencyPairRepository;

    protected static Document getPage(String dateString) throws IOException {
        String url = "https://www.cbr.ru/currency_base/daily/?UniDbQuery.Posted=True&UniDbQuery.To="+dateString;
        return Jsoup.parse(new URL(url), 10000);
    }

    static Document getTodayPage() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        try {
            return getPage(date);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static List<CurrencyDataGetDto> parsePage(Document page) {
        Element table = page.selectFirst("table[class=data]");
        if (table != null) {
            Elements currencyData = table.select("tr");
            List<CurrencyDataGetDto> currencies = new ArrayList<>();

            for (Element currencyDatum : currencyData) {
                Elements currencyDataString = currencyDatum.select("td");
                if (currencyDataString.size() == 0) continue;

                CurrencyDataGetDto currency = new CurrencyDataGetDto();
                currency.setDigitalCode(Integer.parseInt(currencyDataString.get(0).text()));
                currency.setStringCode(currencyDataString.get(1).text());
                currency.setNumOfRurs(Integer.parseInt(currencyDataString.get(2).text()));
                currency.setName(currencyDataString.get(3).text());
                currency.setExchageRate(Float.parseFloat(currencyDataString.get(4).text().replace(',', '.')));
                currencies.add(currency);
            }

            currencies.forEach(System.out::println);
            return currencies;
        } else return null;
    }

    private float getExchangeRate(String stringCodeBase, String stringCodeQuoted) {
        List<CurrencyDataGetDto> currencies = parsePage(Objects.requireNonNull(getTodayPage()));
        CurrencyDataGetDto base = currencies.stream().filter(c -> c.getStringCode().equals(stringCodeBase)).findFirst().get();
        CurrencyDataGetDto quoted = currencies.stream().filter(c -> c.getStringCode().equals(stringCodeQuoted)).findFirst().get();

        return base.getExchageRate() / quoted.getExchageRate();
    }

    public void updateExchangeRates() {
        List<CurrencyPair> pairsFromDB = currencyPairRepository.findAll();
        for (CurrencyPair pair : pairsFromDB) {
            int pairId = currencyPairRepository
                    .findByBaseCharcodeAndQuotedCharcode(
                            pair.getBaseCharcode(), pair.getQuotedCharcode()
                    )
                    .getId();
            float newRate = getExchangeRate(pair.getBaseCharcode(), pair.getQuotedCharcode());
            ExchangeRate rateToUpdate = exchangeRateRepository.findByCurrencyPairId(pairId);
            exchangeRateRepository.updateRateValueAndDate(rateToUpdate.getId(), newRate, LocalDateTime.now());
        }
    }

//    public ExchangeRate getOneRate(int pairId) {
//        ExchangeRate oldRate = exchangeRateRepository.findByCurrencyPairId(pairId);
//        return oldRate;
//    }

}

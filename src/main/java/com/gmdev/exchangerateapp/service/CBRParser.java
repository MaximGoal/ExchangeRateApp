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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CBRParser implements InitializingBean {

    private static CBRParser instance;
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyPairRepository currencyPairRepository;

    protected Document getPage(String dateString) throws IOException {
        String url = "https://www.cbr.ru/currency_base/daily/?UniDbQuery.Posted=True&UniDbQuery.To="+dateString;
        return Jsoup.parse(new URL(url), 10000);
    }

    protected Document getTodayPage() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        try {
            return getPage(date);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<CurrencyDataGetDto> parsePage(Document page) {
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

    protected float getExchangeRate(String stringCodeBase, String stringCodeQuoted) {
        List<CurrencyDataGetDto> currencies = parsePage(Objects.requireNonNull(getTodayPage()));
        CurrencyDataGetDto base = currencies.stream()
                .filter(c -> c.getStringCode().equals(stringCodeBase))
                .findFirst().orElse(null);
        CurrencyDataGetDto quoted = currencies.stream()
                .filter(c -> c.getStringCode().equals(stringCodeQuoted))
                .findFirst().orElse(null);

        float result = 0.0F;

        if (base != null && quoted != null)
            result = base.getExchageRate() / quoted.getExchageRate();
        else if (quoted != null && stringCodeBase.equals("RUR"))
            result = quoted.getExchageRate();
        else if (base != null && stringCodeQuoted.equals("RUR"))
            result = 1 / base.getExchageRate();

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateExchangeRates() {

//        List<CurrencyPair> pairsFromDB = currencyPairRepository.findAll();
        List<CurrencyPair> pairsFromDB = currencyPairRepository.findAllUnique();

        for (CurrencyPair pair : pairsFromDB) {

            float newRate = getExchangeRate(pair.getBaseCharcode(), pair.getQuotedCharcode());

            LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
            Optional<ExchangeRate> rateToUpdate = exchangeRateRepository
                    .findFirstByCurrencyPairAndRateDateAfter(pair,today);
            rateToUpdate.ifPresent(exchangeRate -> exchangeRateRepository.updateRateValueAndDate(exchangeRate.getId(), newRate, LocalDateTime.now()));
//            ExchangeRate rateToUpdate = exchangeRateRepository.findFirstByCurrencyPairId(pair.getId()).orElse(null);

            if (rateToUpdate.isEmpty()
//                    || !rateToUpdate.getRateDate().toLocalDate().equals(today)
//                    || pair.getBaseCharcode().equals("RUR") || pair.getQuotedCharcode().equals("RUR")
            ) {
                ExchangeRate newExchangeRate = new ExchangeRate();
                newExchangeRate.setRateDate(LocalDateTime.now());
                newExchangeRate.setRateValue(newRate);
                newExchangeRate.setCurrencyPair(pair);

                exchangeRateRepository.save(newExchangeRate);
            }
//            Optional<ExchangeRate> rateTodayToUpdate = exchangeRateRepository.findByCurrencyPairAndAndRateDate(pair, today);
//            rateTodayToUpdate.ifPresent(exchangeRate -> exchangeRateRepository.updateRateValueAndDate(exchangeRate.getId(), newRate, LocalDateTime.now()));

//            if (rateToUpdate != null && rateToUpdate.getRateDate().toLocalDate().equals(today)) {
//                exchangeRateRepository.updateRateValueAndDate(rateToUpdate.getId(), newRate, LocalDateTime.now());
//            } else if (rateToUpdate == null ||
//                    !rateToUpdate.getRateDate().toLocalDate().equals(today) ||
//                    pair.getBaseCharcode().equals("RUR") || pair.getQuotedCharcode().equals("RUR")) {
//                ExchangeRate newExchangeRate = new ExchangeRate();
//                newExchangeRate.setRateDate(LocalDateTime.now());
//                newExchangeRate.setRateValue(newRate);
//                newExchangeRate.setCurrencyPair(pair);
//
//                exchangeRateRepository.save(newExchangeRate);
//            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
    }

    public static CBRParser get() {
        return instance;
    }

//    public ExchangeRate getOneRate(int pairId) {
//        ExchangeRate oldRate = exchangeRateRepository.findByCurrencyPairId(pairId);
//        return oldRate;
//    }

}

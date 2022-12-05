package com.gmdev.exchangerateapp.controller;

import com.gmdev.exchangerateapp.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/сurrencyws")
@RequiredArgsConstructor
public class CurrencyWSController {

    private final ExchangeRatesService service;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Float> getExchangeRate (int currencyPairId, LocalDate date) {
        Float response = service.getRateByCurrencyPairIdAndDate(currencyPairId, date);

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Float> getExchangeRate (int currencyPairId) {
        Float response = service.getRateByCurrencyPairId(currencyPairId);

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity getCurrencyPairs () {

    }

    @PostMapping
    public ResponseEntity addCurrencyPairs () {

    }
}

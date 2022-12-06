package com.gmdev.exchangerateapp.controller;

import com.gmdev.exchangerateapp.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/сurrencyws")
@RequiredArgsConstructor
public class CurrencyWSController {

    private final ExchangeRatesService service;

    @GetMapping(value = "/getExchangeRate/{currencyPairId}/{date}",
            produces = "application/json")
    public ResponseEntity<Float> getExchangeRate (@PathVariable Integer currencyPairId, @PathVariable LocalDate date) {
        Float response = service.getRateByCurrencyPairIdAndDate(currencyPairId, date);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getExchangeRate/{currencyPairId}",
            produces = "application/json")
    public ResponseEntity<Float> getExchangeRate (@PathVariable Integer currencyPairId) {
        Float response = service.getRateByCurrencyPairId(currencyPairId);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getCurrencyPairs",
            produces = "application/json")
    public ResponseEntity<HttpStatus> getCurrencyPairs () {
        return null;
    }

    @PostMapping(value = "/addCurrencyPairs")
    public ResponseEntity<HttpStatus> addCurrencyPairs () {
        return null;
    }
}

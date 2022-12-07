package com.gmdev.exchangerateapp.controller;

import com.gmdev.exchangerateapp.dto.CurrencyPairGetDTO;
import com.gmdev.exchangerateapp.model.CurrencyPair;
import com.gmdev.exchangerateapp.service.CurrencyPairService;
import com.gmdev.exchangerateapp.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/сurrencyws")
@RequiredArgsConstructor
public class CurrencyWSController {

    private final ExchangeRatesService ratesService;
    private final CurrencyPairService currencyPairService;

    @GetMapping(value = "/getExchangeRate/{currencyPairId}/{date}",
            produces = "application/json")
    public ResponseEntity<Float> getExchangeRate (@PathVariable Integer currencyPairId,
                                                  @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        Float response = ratesService.getRateByCurrencyPairIdAndDate(currencyPairId, date);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getExchangeRate/{currencyPairId}", produces = "application/json")
    public ResponseEntity<Float> getExchangeRate (@PathVariable Integer currencyPairId) {
        Float response = ratesService.getRateByCurrencyPairId(currencyPairId);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getCurrencyPairs", produces = "application/json")
    public ResponseEntity<List<CurrencyPair>> getCurrencyPairs () {
        List<CurrencyPair> currencyPairList = currencyPairService.getAllPairs();
        return ResponseEntity.ok(currencyPairList);
    }

    @PostMapping(value = "/addCurrencyPairs", consumes = "application/json", produces = "application/json")
    public ResponseEntity<HttpStatus> addCurrencyPairs (@RequestBody CurrencyPairGetDTO currencyPairGetDTO) {

        Optional <CurrencyPair> maybeCurrencyPairIntoDB = currencyPairService
                .findByBaseAndQuotedCharcode(
                        currencyPairGetDTO.getBaseCharcode(),
                        currencyPairGetDTO.getQuotedCharcode());

        if (maybeCurrencyPairIntoDB.isPresent())
            return ResponseEntity.ok(HttpStatus.FOUND);
        else {
            CurrencyPair newCurrencyPair = CurrencyPair.builder()
                    .baseCharcode(currencyPairGetDTO.getBaseCharcode())
                    .quotedCharcode(currencyPairGetDTO.getQuotedCharcode())
                    .build();
            currencyPairService.save(newCurrencyPair);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<HttpStatus> handleNullPointerException() {
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }
}

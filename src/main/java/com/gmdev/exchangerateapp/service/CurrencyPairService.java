package com.gmdev.exchangerateapp.service;

import com.gmdev.exchangerateapp.model.CurrencyPair;
import com.gmdev.exchangerateapp.repository.CurrencyPairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurrencyPairService {

    private final CurrencyPairRepository currencyPairRepository;

    public List<CurrencyPair> getAllPairs () {
        return currencyPairRepository.findAllUnique();
    }

    public Optional<CurrencyPair> findByBaseAndQuotedCharcode(String base, String quoted) {
        return currencyPairRepository.findFirstByBaseCharcodeAndQuotedCharcode(base, quoted);
    }

    @Transactional(readOnly = false)
    public void save(CurrencyPair newCurrencyPair) {
        currencyPairRepository.save(newCurrencyPair);
    }
}

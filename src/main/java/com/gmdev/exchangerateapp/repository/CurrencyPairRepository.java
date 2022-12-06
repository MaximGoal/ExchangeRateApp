package com.gmdev.exchangerateapp.repository;

import com.gmdev.exchangerateapp.model.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Integer> {

    CurrencyPair findByBaseCharcodeAndQuotedCharcode(String base, String quoted);

    @Query("select distinct cp from CurrencyPair cp ")
    List<CurrencyPair> findAllUnique();

}

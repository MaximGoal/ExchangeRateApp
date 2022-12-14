package com.gmdev.exchangerateapp.repository;

import com.gmdev.exchangerateapp.model.CurrencyPair;
import com.gmdev.exchangerateapp.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByCurrencyPairAndAndRateDate (CurrencyPair currencyPair, LocalDateTime dateTime);
    Optional<ExchangeRate> findByCurrencyPairAndAndRateDateBetween (CurrencyPair currencyPair, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
    Optional<ExchangeRate> findFirstByCurrencyPairAndRateDateAfter (CurrencyPair currencyPair, LocalDateTime dateTime);

    Optional<ExchangeRate> findFirstByCurrencyPairOrderByRateDateDesc(CurrencyPair currencyPair);

    @Query("UPDATE ExchangeRate ex SET ex.rateValue = :value, ex.rateDate = :dateTime WHERE ex.id = :id")
    @Modifying
    @Transactional
    void updateRateValueAndDate(@Param("id") long id, @Param("value") float value, @Param("dateTime") LocalDateTime dateTime);


}

package com.gmdev.exchangerateapp.repository;

import com.gmdev.exchangerateapp.model.ExchangeRate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    ExchangeRate findByCurrencyPairIdAndAndRateDate(int id, LocalDateTime dateTime);

    ExchangeRate findByCurrencyPairId(int id);

    @Query("UPDATE ExchangeRate ex SET ex.rateValue = :value, ex.rateDate = :dateTime WHERE ex.id = :id")
    void updateRateValueAndDate(@Param("id") long id, @Param("value") float value, @Param("dateTime") LocalDateTime dateTime);
}

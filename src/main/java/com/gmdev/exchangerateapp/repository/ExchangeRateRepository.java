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

    public ExchangeRate findByCurrencyPairIdAndAndRateDate(int id, LocalDateTime dateTime);

    public ExchangeRate findByCurrencyPairId(int id);

    @Query("UPDATE exchange_rate ex SET ex.rate_value = :value, ex.rate_date = :dateTime WHERE ex.id = :id")
    public void updateRateValueAndDate(@Param("id") long id, @Param("value") float value, @Param("dateTime") LocalDateTime dateTime);
}

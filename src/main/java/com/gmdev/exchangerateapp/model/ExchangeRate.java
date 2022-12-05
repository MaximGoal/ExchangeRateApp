package com.gmdev.exchangerateapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rate")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExchangeRate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate_date")
    private LocalDateTime rateDate;

    @Column(name = "rate_value")
    private float rateValue;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "currency_pair_id", referencedColumnName = "id")
    private CurrencyPair currencyPair;

    public ExchangeRate() {
    }

    public ExchangeRate(LocalDateTime rateDate, float rateValue, CurrencyPair currencyPair) {
        this.rateDate = rateDate;
        this.rateValue = rateValue;
        this.currencyPair = currencyPair;
    }
}

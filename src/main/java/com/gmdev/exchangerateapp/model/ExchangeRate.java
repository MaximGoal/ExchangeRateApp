package com.gmdev.exchangerateapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "exchange_rate")
@Getter
@Setter
@NoArgsConstructor
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_pair_id", referencedColumnName = "id")
    private CurrencyPair currencyPairId;
}

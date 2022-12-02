package com.gmdev.exchangerateapp.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Date rateDate;

    @Column(name = "rate_float")
    private float rate_value;

    @Column(name = "currency_pair_id")
    private int currencyPairId;
}

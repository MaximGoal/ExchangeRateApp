package com.gmdev.exchangerateapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "currency_pair")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class CurrencyPair {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "base_charcode")
    @Size(max = 5, message = "baseCharcode size must be less than 5 chars")
    private String baseCharcode;

    @Column(name = "quoted_charcode")
    @Size(max = 5, message = "quotedCharcode size must be less than 5 chars")
    private String quotedCharcode;

    @Column(name = "description")
    @Size(max = 100, message = "description size must be less than 100 chars")
    private String description;

}

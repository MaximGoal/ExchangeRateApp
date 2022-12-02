package com.gmdev.exchangerateapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyDataGetDto {

    private int digitalCode;
    private String stringCode;
    private int numOfRurs;
    private String name;
    private float exchageRate;
}

package com.gmdev.exchangerateapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyPairGetDTO {
    String baseCharcode;
    String quotedCharcode;
}

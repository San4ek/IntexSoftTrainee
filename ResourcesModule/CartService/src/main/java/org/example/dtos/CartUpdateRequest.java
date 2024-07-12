package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.CurrencyEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateRequest {

     private CurrencyEnum currency;
}

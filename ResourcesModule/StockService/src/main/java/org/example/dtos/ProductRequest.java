package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;

    private TypeEnum type;

    private CurrencyEnum currency;

    private float price;

    private UUID brandId;

}

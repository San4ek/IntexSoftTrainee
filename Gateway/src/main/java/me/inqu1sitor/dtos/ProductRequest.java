package me.inqu1sitor.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.inqu1sitor.dtos.enums.CurrencyEnum;
import me.inqu1sitor.dtos.enums.TypeEnum;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;

    private TypeEnum type;

    private CurrencyEnum currency;

    private Double price;

    private UUID brandId;
}

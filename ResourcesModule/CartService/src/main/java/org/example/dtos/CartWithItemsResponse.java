package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.CurrencyEnum;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartWithItemsResponse {

    private UUID id;

    private CurrencyEnum currency;

    private List<StockItemAmount> cartItems;
}

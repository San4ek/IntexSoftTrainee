package org.example.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockItemAmount {

    private UUID stockItemId;

    private Long amount;

    private Double price;
}

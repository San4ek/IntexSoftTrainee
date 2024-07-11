package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockItemResponse {

    private UUID id;

    private ColorEnum color;

    private SizeEnum size;

    private ProductResponse product;

    private Long amount;


}

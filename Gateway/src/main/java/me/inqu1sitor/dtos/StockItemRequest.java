package me.inqu1sitor.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.inqu1sitor.dtos.enums.ColorEnum;
import me.inqu1sitor.dtos.enums.SizeEnum;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockItemRequest {

    private ColorEnum color;

    private SizeEnum size;

    private UUID productId;

    private Long amount;
}

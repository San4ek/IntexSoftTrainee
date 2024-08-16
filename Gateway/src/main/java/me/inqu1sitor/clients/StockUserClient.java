package me.inqu1sitor.clients;

import me.inqu1sitor.dtos.enums.ColorEnum;
import me.inqu1sitor.dtos.enums.SizeEnum;
import me.inqu1sitor.dtos.enums.StockOperationEnum;
import me.inqu1sitor.dtos.enums.TypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "stock-user-client", url = "http://stock-service:8083/api/user-stock")
public interface StockUserClient {

    @GetMapping("/name/{name}")
    ResponseEntity<Object> getStockItemsByName(@PathVariable("name") String name);

    @GetMapping("/attributes")
    ResponseEntity<Object> getStockItemsByAttributes(@RequestParam(value = "brand", required = false) String brand,
                                                     @RequestParam(value = "color", required = false) ColorEnum color,
                                                     @RequestParam(value = "size", required = false) SizeEnum size,
                                                     @RequestParam(value = "type", required = false) TypeEnum type,
                                                     @RequestParam(value = "minPrice", required = false) Double minPrice,
                                                     @RequestParam(value = "maxPrice", required = false) Double maxPrice);

    @GetMapping("/{stockItemId}")
    ResponseEntity<Object> checkStockItemAmount(@PathVariable("stockItemId") UUID stockItemId);

    @PutMapping("/{stockItemId}")
    ResponseEntity<Object> changeStockAmount(@PathVariable("stockItemId") UUID stockItemId, @RequestParam("amount") Long amount, @RequestParam("operation") StockOperationEnum operation);
}

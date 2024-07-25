package me.inqu1sitor.clients;

import me.inqu1sitor.dtos.CartItemRequest;
import me.inqu1sitor.dtos.CartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "cart-client", url = "http://localhost:8082/api/carts")
public interface CartClient {

    @GetMapping("/{cartId}")
    ResponseEntity<Object> getCart(@PathVariable("cartId") UUID cartId);

    @PostMapping
    ResponseEntity<Object> createCart(@RequestBody CartRequest cartRequest);

    @PutMapping("/{cartId}")
    ResponseEntity<Object> updateCart(@PathVariable("cartId") UUID cartId, @RequestBody CartRequest cartRequest);

    @PostMapping("/item")
    ResponseEntity<Object> addItemInCart(@RequestBody CartItemRequest cartItemRequest);

    @DeleteMapping("/item")
    ResponseEntity<Object> deleteItemFromCart(@RequestParam("cartId") UUID cartId, @RequestParam("stockItemId") UUID stockItemId);
}

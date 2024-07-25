package me.inqu1sitor.clients;

import me.inqu1sitor.dtos.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "order-client", url = "http://localhost:8082/api/orders")
public interface OrderClient {

    @GetMapping("/{orderId}")
    ResponseEntity<Object> getOrder(@PathVariable("orderId") UUID orderId);

    @PostMapping
    ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest);

    @DeleteMapping("/{orderId}")
    ResponseEntity<Object> deleteOrder(@PathVariable("orderId") UUID orderId);

    @DeleteMapping("/address/{addressId}")
    ResponseEntity<Object> deleteOrdersWithAddress(@PathVariable("addressId") UUID addressId);
}

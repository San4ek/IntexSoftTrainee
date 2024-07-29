package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.CartItemRequest;
import org.example.dtos.StockItemAmount;
import org.example.repositories.CartItemRepository;
import org.example.repositories.CartRepository;
import org.example.services.impl.StockServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkTrue;

@Service
@RequiredArgsConstructor
public class ValidationCartService {

    private final CartRepository cartRepository;
    private final StockServiceImpl stockService;
    private final CartItemRepository cartItemRepository;

    public void validateCartForUpdate(UUID cartId) {
        checkTrue(cartRepository.existsById(cartId), "Cart is not exist");
    }

    public void validateCartForAddItem(CartItemRequest cartItemRequest) {
        checkTrue(cartRepository.existsById(cartItemRequest.getCartId()), "Cart is not exist");
        StockItemAmount stockItemAmount = stockService.getStockItemById(cartItemRequest.getStockId());
        checkTrue(stockItemAmount.getAmount() >= cartItemRequest.getAmount(), "Not enough items in stock");
    }

    public void validateCartForDeleteItem(UUID cartId, UUID stockItemId) {
        checkTrue(cartRepository.existsById(cartId), "Cart is not exist");
        checkTrue(cartItemRepository.existsByCartIdAndStockId(cartId, stockItemId), "No items with such id in cart");
    }

    public void validateCartForDelete(UUID cartId) {
        checkTrue(cartRepository.existsById(cartId), "Cart is not exist");
    }
}

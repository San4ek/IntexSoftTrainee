package org.example.validation;

import lombok.RequiredArgsConstructor;
import org.example.dtos.CartItemRequest;
import org.example.dtos.StockItemAmount;
import org.example.repositories.CartItemRepository;
import org.example.repositories.CartRepository;
import org.example.services.impl.StockServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.example.utils.validation.ValidatorUtils.checkFalse;
import static org.example.utils.validation.ValidatorUtils.checkTrue;

/**
 * Class for validating cart operations.
 */
@Service
@RequiredArgsConstructor
public class ValidationCartService {

    private final CartRepository cartRepository;
    private final StockServiceImpl stockService;
    private final CartItemRepository cartItemRepository;

    @Value("${cart.apiKey}")
    private String correctKey;

    /**
     * Validates the cart for update operation.
     *
     * @param cartId the ID of the cart to validate
     */
    public void validateCartForUpdate(final UUID cartId) {
        checkTrue(cartRepository.existsById(cartId), "Cart is not exist");
    }

    /**
     * Validates the cart for adding an item.
     *
     * @param cartItemRequest the request containing the cart ID and the item to add
     */
    public void validateCartForAddItem(final CartItemRequest cartItemRequest) {
        checkTrue(cartRepository.existsById(cartItemRequest.getCartId()), "Cart is not exist");
        checkFalse(cartItemRepository.existsByCartIdAndStockId(cartItemRequest.getCartId(), cartItemRequest.getStockId()), "Cart items are already in cart");
        final StockItemAmount stockItemAmount = stockService.getStockItemById(cartItemRequest.getStockId());
        checkTrue(stockItemAmount.getAmount() >= cartItemRequest.getAmount(), "Not enough items in stock");
    }

    /**
     * Validates the cart for deleting an item.
     *
     * @param cartId the UUID of the cart
     * @param stockItemId the UUID of the stock item to delete
     */
    public void validateCartForDeleteItem(final UUID cartId, final UUID stockItemId) {
        checkTrue(cartRepository.existsById(cartId), "Cart is not exist");
        checkTrue(cartItemRepository.existsByCartIdAndStockId(cartId, stockItemId), "No items with such id in cart");
    }

    /**
     * Validates the cart for delete operation.
     *
     * @param cartId the ID of the cart to validate.
     */
    public void validateCartForDelete(final UUID cartId) {
        checkTrue(cartRepository.existsByUserId(cartId), "Cart is not exist");
    }

    /**
     * Method for validating key used between microservices.
     *
     * @param apiKey key for checking where did request come from.
     */
    public void validateApiKey(final String apiKey) {
        checkTrue(correctKey.equals(apiKey), "Access denied.");
    }
}

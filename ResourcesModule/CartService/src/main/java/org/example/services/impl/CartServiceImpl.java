package org.example.services.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.CartItemRequest;
import org.example.dtos.CartRequest;
import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;
import org.example.enums.StockOperationEnum;
import org.example.mappers.CartItemMapper;
import org.example.mappers.CartMapper;
import org.example.repositories.CartItemRepository;
import org.example.repositories.CartRepository;
import org.example.repositories.OrderRepository;
import org.example.services.CartService;
import org.example.validation.ValidationCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Service implementation for shopping carts.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;
    private final StockServiceImpl stockService;
    private final ValidationCartService validationCartService;

    /**
     * Retrieves a cart by its ID.
     *
     * @param cartId the ID of the cart to retrieve.
     * @return the cart entity.
     */
    @Override
    @Transactional(readOnly = true)
    public CartEntity getCart(final UUID cartId) {
        return cartRepository.getById(cartId);

    }

    /**
     * Creates a new cart.
     *
     * @param cartRequest the request object containing cart details.
     * @return the created cart entity.
     */
    @Override
    @Transactional
    public CartEntity createCart(@Valid final CartRequest cartRequest) {
        log.info("Creating new cart");
        return cartRepository.save(cartMapper.toEntity(cartRequest));
    }

    /**
     * Updates an existing cart.
     *
     * @param cartId the ID of the cart to update.
     * @param cartRequest the request object containing updated cart details.
     * @return the updated cart entity.
     */
    @Override
    @Transactional
    public CartEntity updateCart(final UUID cartId, @Valid final CartRequest cartRequest) {
        log.info("Updating cart entity with id {}", cartId);
        validationCartService.validateCartForUpdate(cartId);
        CartEntity cartEntity = cartRepository.getById(cartId);
        cartMapper.toEntity(cartEntity, cartRequest);
        return cartRepository.save(cartEntity);
    }

    /**
     * Adds an item to a cart.
     *
     * @param cartItemRequest the request object containing cart item details.
     */
    @Override
    @Transactional
    public CartItemEntity addItemInCart(@Valid final CartItemRequest cartItemRequest) {
        log.info("Adding item {} in cart with id {}", cartItemRequest.getStockId(),cartItemRequest.getCartId());
        validationCartService.validateCartForAddItem(cartItemRequest);
        CartItemEntity cartItemEntity = cartItemMapper.toEntity(cartItemRequest);
        CartEntity cartEntity = cartRepository.getById(cartItemRequest.getCartId());
        cartEntity.addItems(cartItemEntity);
        cartRepository.save(cartEntity);
        stockService.changeStockAmount(cartItemRequest.getStockId(), cartItemRequest.getAmount(), StockOperationEnum.DECREASE);
        return cartItemEntity;
    }

    /**
     * Deletes an item from a cart.
     *
     * @param cartId the ID of the cart.
     * @param stockItemId the ID of the stock item to delete.
     */
    @Override
    @Transactional
    public void deleteItemFromCart(final UUID cartId, final UUID stockItemId) {
        log.info("Deleting item {} from cart with id {}", stockItemId, cartId);
        validationCartService.validateCartForDeleteItem(cartId, stockItemId);
        CartEntity cartEntity = cartRepository.getById(cartId);
        CartItemEntity cartItemEntity = cartItemRepository.findByCartIdAndStockId(cartId, stockItemId);
        cartEntity.removeItems(cartItemEntity);
        cartItemRepository.delete(cartItemEntity);
        cartRepository.save(cartEntity);
        stockService.changeStockAmount(stockItemId, cartItemEntity.getAmount(), StockOperationEnum.INCREASE);
    }

    /**
     * Deletes all items with ID from all carts.
     *
     * @param stockId the ID of the item to delete.
     */
    @Override
    @Transactional
    public void deleteCartItemsByStockId(final UUID stockId) {
        log.info("Deleting cart items with stock id {}", stockId);
        cartItemRepository.deleteByStockId(stockId);
    }

    @Override
    @Transactional
    public void deleteByUserId(final UUID userId) {
        log.info("Deleting cart with user id {}", userId);
        validationCartService.validateCartForDelete(userId);
        final CartEntity cartEntity = cartRepository.findByUserId(userId);
        cartRepository.deleteByUserId(userId);
        for (CartItemEntity cartItem : cartEntity.getCartItems()) {
            stockService.changeStockAmount(cartItem.getStockId(), cartItem.getAmount(), StockOperationEnum.INCREASE);
        }
        orderRepository.deleteByCartId(cartEntity.getId());
    }
}

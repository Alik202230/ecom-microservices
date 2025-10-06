package am.itspace.order.service;

import am.itspace.order.client.ProductServiceClient;
import am.itspace.order.client.UserServiceClient;
import am.itspace.order.dto.CartItemRequest;
import am.itspace.order.dto.ProductResponse;
import am.itspace.order.dto.UserResponse;
import am.itspace.order.mapper.CartItemMapper;
import am.itspace.order.model.CartItem;
import am.itspace.order.repository.CartItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartItemService {

  private final CartItemRepository cartItemRepository;
  private final CartItemMapper cartItemMapper;
  private final ProductServiceClient productServiceClient;
  private final UserServiceClient userServiceClient;

  //  @CircuitBreaker(name = "productService", fallbackMethod = "addToCartFallBack")
  @Retry(name = "retryBreaker", fallbackMethod = "addToCartFallBack")
  public boolean addToCart(Long userId, CartItemRequest request) {
    int attempts = 0;
    log.info("ATTEMPT COUNT: {}", ++attempts);

    ProductResponse productResponse = this.productServiceClient.getProductDetails(request.getProductId());
    if (productResponse == null) return false;
    if (productResponse.getStock() < request.getQuantity()) return false;

    UserResponse userResponse = this.userServiceClient.getUserDetails(userId);
    if (userResponse == null) return false;

    CartItem existingCartItem = this.cartItemRepository.findByUserIdAndProductId(userResponse.getId(), request.getProductId());
    if (existingCartItem != null) {
      // Update quantity and price
      existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
      existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
      this.cartItemRepository.save(existingCartItem);
    } else {
      // Add item if not exist
      CartItem cartItem = this.cartItemMapper.toCartItem(request, userId, request.getProductId());
      cartItem.setPrice(BigDecimal.valueOf(1000.00));
      this.cartItemRepository.save(cartItem);
    }
    return true;
  }

  public boolean addToCartFallBack(Long userId, CartItemRequest request, Exception exception) {
    log.info("FALLBACK CALLED");
    return false;
  }

  public boolean deleteProductFromCart(Long userId, Long productId) {
    CartItem cartItem = this.cartItemRepository.findByUserIdAndProductId(userId, productId);
    if (cartItem != null) {
      this.cartItemRepository.delete(cartItem);
      return true;
    }
    return false;
  }

  public List<CartItem> getCart(Long userId) {
    return this.cartItemRepository.findByUserId(userId);
  }

  public void clearCart(Long userId) {
    this.cartItemRepository.deleteByUserId(userId);
  }

}

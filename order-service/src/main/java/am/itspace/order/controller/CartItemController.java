package am.itspace.order.controller;

import am.itspace.order.dto.CartItemRequest;
import am.itspace.order.model.CartItem;
import am.itspace.order.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart-items")
public class CartItemController {

  private static final String PRODUCT_OR_USER_NOT_FOUND = "Product or User not found";
  private static final String X_USER_ID_HEADER = "X-User-ID";
  private final CartItemService cartItemService;

  @PostMapping
  public ResponseEntity<String> addToCart(@RequestHeader(X_USER_ID_HEADER) Long userId, @RequestBody CartItemRequest request) {
    boolean response = this.cartItemService.addToCart(userId, request);
    return response ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(PRODUCT_OR_USER_NOT_FOUND);
  }

  @DeleteMapping("/remove/{productId}")
  public ResponseEntity<Void> removeFromCart(@RequestHeader(X_USER_ID_HEADER) String userId, @PathVariable Long productId) {
    boolean deletedItem = this.cartItemService.deleteProductFromCart(Long.valueOf(userId), productId);
    return deletedItem ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<CartItem>> getCart(@RequestHeader(X_USER_ID_HEADER) String userId) {
    return ResponseEntity.ok(this.cartItemService.getCart(Long.valueOf(userId)));
  }

}

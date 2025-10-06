package am.itspace.order.controller;

import am.itspace.order.dto.OrderResponse;
import am.itspace.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private static final String X_USER_ID_HEADER = "X-User-ID";
  private final OrderService orderService;

  @PostMapping("/place-order")
  public ResponseEntity<Optional<OrderResponse>> createOrder(@RequestHeader(X_USER_ID_HEADER) String userId) {
    Optional<OrderResponse> order = this.orderService.placeOrder(Long.valueOf(userId));
    return ResponseEntity.status(HttpStatus.CREATED).body(order);
  }

}

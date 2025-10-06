package am.itspace.order.service;

import am.itspace.order.dto.OrderResponse;
import am.itspace.order.mapper.OrderMapper;
import am.itspace.order.model.CartItem;
import am.itspace.order.model.Order;
import am.itspace.order.model.OrderItem;
import am.itspace.order.model.enums.OrderStatus;
import am.itspace.order.producer.OrderEventsProducer;
import am.itspace.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final CartItemService cartItemService;
  private final OrderMapper orderMapper;
  private final OrderEventsProducer orderEventsProducer;

  public Optional<OrderResponse> placeOrder(Long userId) {

    log.info("Retrieving product and user with product id: {}", userId);
    List<CartItem> cartItems = this.cartItemService.getCart(userId);
    if (cartItems.isEmpty()) {
      log.warn("No cart items found for user with product id: {}", userId);
      return Optional.empty();
    }

//    Optional<User> optionalUser = this.userRepository.findById(userId);
//    if (optionalUser.isEmpty()) {
//      return Optional.empty();
//    }
//    User user = optionalUser.get();


    BigDecimal totalPrice = cartItems.stream()
        .map(price -> price.getPrice())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    Order order = Order.builder()
        .userId(userId)
        .totalPrice(totalPrice)
        .orderStatus(OrderStatus.CONFIRMED)
        .build();

    List<OrderItem> orderItems = cartItems.stream()
        .map(item -> new OrderItem(null, item.getProductId(), item.getQuantity(), item.getPrice(), order))
        .toList();

    order.setItems(orderItems);
    Order savedOrder = this.orderRepository.save(order);
    cartItemService.clearCart(userId);

    OrderResponse response = this.orderMapper.toOrderResponse(savedOrder);
    this.orderEventsProducer.sendOrderEvent(response);

    return Optional.of(response);
  }
}

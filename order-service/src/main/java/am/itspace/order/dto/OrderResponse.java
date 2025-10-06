package am.itspace.order.dto;

import am.itspace.order.model.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

  private Long id;
  private BigDecimal totalPrice;
  private OrderStatus status;

  @ToString.Exclude
  private List<OrderItemDto> items;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}

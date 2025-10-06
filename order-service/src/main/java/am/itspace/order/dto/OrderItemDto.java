package am.itspace.order.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
  private Long id;
  private Long productId;
  private Integer quantity;
  private BigDecimal price;
}

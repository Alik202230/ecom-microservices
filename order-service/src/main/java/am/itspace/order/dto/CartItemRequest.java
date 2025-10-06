package am.itspace.order.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
  private Long productId;
  private Integer quantity;
}

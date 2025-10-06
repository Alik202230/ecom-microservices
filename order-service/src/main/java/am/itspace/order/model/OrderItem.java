package am.itspace.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private Integer quantity;
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;
}

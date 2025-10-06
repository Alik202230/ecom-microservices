package am.itspace.order.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stock;
  private String category;
  private String imageUrl;
  private Boolean isActive;
}

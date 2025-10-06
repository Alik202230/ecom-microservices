package am.itspace.product.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stock;
  private String category;
  private String imageUrl;
}

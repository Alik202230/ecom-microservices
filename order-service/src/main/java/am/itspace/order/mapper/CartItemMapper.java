package am.itspace.order.mapper;

import am.itspace.order.dto.CartItemRequest;
import am.itspace.order.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "price", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  CartItem toCartItem(CartItemRequest request, Long userId, Long productId);

}

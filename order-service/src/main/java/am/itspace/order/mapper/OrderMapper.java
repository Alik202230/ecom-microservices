package am.itspace.order.mapper;

import am.itspace.order.dto.OrderResponse;
import am.itspace.order.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  @Mapping(target = "status", source = "orderStatus")
  OrderResponse toOrderResponse(Order order);
}

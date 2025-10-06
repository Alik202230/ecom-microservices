package am.itspace.product.mapper;

import am.itspace.product.dto.ProductRequest;
import am.itspace.product.dto.ProductResponse;
import am.itspace.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "stockQuantity", source = "stock")
  Product toProductRequest(ProductRequest product);

  @Mapping(target = "stock", source = "stockQuantity")
  ProductResponse toProductResponse(Product product);

  @Mapping(target = "stockQuantity", source = "stock")
  void toUpdateProduct(ProductRequest request, @MappingTarget Product existingProduct);
}

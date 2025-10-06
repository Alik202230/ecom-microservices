package am.itspace.product.service;

import am.itspace.product.dto.ProductRequest;
import am.itspace.product.dto.ProductResponse;
import am.itspace.product.mapper.ProductMapper;
import am.itspace.product.model.Product;
import am.itspace.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductResponse createProduct(ProductRequest request) {
    Product product = this.productMapper.toProductRequest(request);

    final Boolean isActive = product.getIsActive() == null ? Boolean.TRUE : product.getIsActive();

    product.setIsActive(isActive);
    Product savedProduct = this.productRepository.save(product);

    log.info("Product with id: {} persisted", savedProduct.getId());

    return this.productMapper.toProductResponse(savedProduct);
  }

  public Optional<ProductResponse> updateProduct(ProductRequest request, Long id) {
    log.info("Updating product with id: {}", id);
    return this.productRepository.findById(id)
        .map(existingProduct -> {
          this.productMapper.toUpdateProduct(request, existingProduct);
          Product savedProduct = this.productRepository.save(existingProduct);
          return this.productMapper.toProductResponse(savedProduct);
        });
  }

  public List<ProductResponse> getAllProducts() {
    log.info("Retrieving all products");
    return this.productRepository.findByIsActiveTrue().stream()
        .map(product -> this.productMapper.toProductResponse(product))
        .toList();
  }

  public void deleteProduct(Long id) {
    log.info("Deleting product with id: {}", id);
    this.productRepository.findById(id)
        .ifPresentOrElse(product -> this.productRepository.delete(product),
            () -> {
              log.error("Product with id: {} deleted", id);
              throw new IllegalArgumentException("Product not found");
            });
  }

  public List<ProductResponse> searchProduct(String keyword) {
    log.info("Retrieving all products by  keyword: {}", keyword);
    return this.productRepository.searchProducts(keyword).stream()
        .map(product -> this.productMapper.toProductResponse(product))
        .toList();
  }

  public Optional<ProductResponse> getProductById(Long id) {
    log.info("Retrieving product and user with product id: {}", id);
    return this.productRepository.findByIdAndIsActiveTrue(id)
        .map(product -> this.productMapper.toProductResponse(product));
  }
}

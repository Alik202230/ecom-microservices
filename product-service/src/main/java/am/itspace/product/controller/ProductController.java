package am.itspace.product.controller;

import am.itspace.product.dto.ProductRequest;
import am.itspace.product.dto.ProductResponse;
import am.itspace.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping("/create")
  public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
    ProductResponse response = this.productService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/all")
  public ResponseEntity<List<ProductResponse>> getProducts() {
    List<ProductResponse> responses = this.productService.getAllProducts();
    return ResponseEntity.status(HttpStatus.OK).body(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
    return this.productService.getProductById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable Long id) {
    return this.productService.updateProduct(request, id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    this.productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword) {
    return ResponseEntity.ok(this.productService.searchProduct(keyword));
  }

}

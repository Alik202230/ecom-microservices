package am.itspace.product.repository;

import am.itspace.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByIsActiveTrue();

  @Query("SELECT p FROM Product p WHERE p.isActive = true " +
      "AND p.stockQuantity > 0 AND LOWER(p.name) " +
      "LIKE (LOWER(CONCAT('%', :keyword, '%')) )"
  )
  List<Product> searchProducts(@Param("keyword") String keyword);

  Optional<Product> findByIdAndIsActiveTrue(Long id);
}

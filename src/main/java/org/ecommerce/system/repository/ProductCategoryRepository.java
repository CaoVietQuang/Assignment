package org.ecommerce.system.repository;

import org.ecommerce.system.domain.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    @Modifying
    @Query("DELETE FROM ProductCategoryEntity pc WHERE pc.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}

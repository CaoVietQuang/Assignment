package org.ecommerce.system.repository;


import jakarta.persistence.Tuple;
import org.ecommerce.system.domain.entity.ProductCategoryEntity;
import org.ecommerce.system.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findById(Long id);

    Optional<ProductEntity> findByCode(String code);

    List<ProductEntity> findByIsActive(Integer isActive);

    List<ProductEntity> findByPublisherId(Long publisherId);

    @Query("SELECT DISTINCT p FROM ProductEntity p " +
            "JOIN p.productCategories pc " +
            "WHERE pc.category.id = :categoryId AND p.isActive = 0")
    List<ProductEntity> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p.id as productId, p.name as productName, p.code as productCode, " +
            "p.price as price, p.rating as rating, p.imageUrl as imageUrl, " +
            "p.color as color, p.size as size, " +
            "pub.id as publisherId, pub.name as publisherName, " +
            "p.createdAt as createdAt " +
            "FROM ProductEntity p " +
            "LEFT JOIN p.publisher pub " +
            "WHERE pub.isActive = 0 " +
            "ORDER BY p.createdAt DESC")
    List<Tuple> findProductsWithPublisherInfo();

    @Query("SELECT p.id as productId, p.name as productName, p.code as productCode, " +
            "p.price as price, p.rating as rating, p.imageUrl as imageUrl, " +
            "pub.name as publisherName, " +
            "COUNT(DISTINCT f.id) as feedbackCount, " +
            "COALESCE(AVG(f.rating), p.rating, 0.0) as averageRating " +
            "FROM ProductEntity p " +
            "LEFT JOIN p.publisher pub " +
            "LEFT JOIN p.feedbacks f " +
            "WHERE p.isActive = 0 AND p.rating IS NOT NULL AND (pub.isActive = 0 OR pub.isActive IS NULL) " +
            "GROUP BY p.id, p.name, p.code, p.price, p.rating, p.imageUrl, pub.name " +
            "ORDER BY COALESCE(AVG(f.rating), p.rating, 0.0) DESC, p.rating DESC " +
            "LIMIT 10")
    List<Tuple> findTopRatedProducts();

    @Query("SELECT p FROM ProductEntity p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.isActive = 0")
    Page<ProductEntity> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.price <= :maxPrice AND p.isActive = 0")
    Page<ProductEntity> findByPriceRangeWithNullMin(@Param("maxPrice") Double maxPrice, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.price >= :minPrice AND p.isActive = 0")
    Page<ProductEntity> findByPriceRangeWithNullMax(@Param("minPrice") Double minPrice, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.publisher.id = :publisherId AND p.isActive = 0")
    Page<ProductEntity> findByPublisherIdWithPagination(@Param("publisherId") Long publisherId, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.isActive = 0")
    Page<ProductEntity> findAll(Pageable pageable);
}

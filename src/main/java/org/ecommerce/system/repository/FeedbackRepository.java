package org.ecommerce.system.repository;

import org.ecommerce.system.domain.entity.FeedbackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {

    @Query("SELECT f FROM FeedbackEntity f WHERE f.product.id = :productId ORDER BY f.createdAt DESC")
    Page<FeedbackEntity> findByProductId(@Param("productId") Long productId, Pageable pageable);

    @Query("SELECT f FROM FeedbackEntity f WHERE f.userEntity.id = :userId ORDER BY f.createdAt DESC")
    Page<FeedbackEntity> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT AVG(f.rating) FROM FeedbackEntity f WHERE f.product.id = :productId")
    Double findAverageRatingByProductId(@Param("productId") Long productId);

    @Query("SELECT COUNT(f) FROM FeedbackEntity f WHERE f.product.id = :productId")
    Long countByProductId(@Param("productId") Long productId);

    boolean existsByProductIdAndUserEntityId(Long productId, Long userId);
}

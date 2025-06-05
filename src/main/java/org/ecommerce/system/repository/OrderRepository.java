package org.ecommerce.system.repository;

import org.ecommerce.system.domain.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);

    Optional<OrderEntity> findByCode(String code);

    Optional<OrderEntity> findById(Long id);

    @Query("SELECT o FROM OrderEntity o WHERE o.user.id = :userId AND o.orderStatus = :status")
    Page<OrderEntity> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status, Pageable pageable);

}

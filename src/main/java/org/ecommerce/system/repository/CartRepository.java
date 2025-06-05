package org.ecommerce.system.repository;

import org.ecommerce.system.domain.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserId(Long userId);

    @Query("SELECT c FROM CartEntity c WHERE c.user.id = :userId")
    Optional<CartEntity> findCartByUserId(@Param("userId") Long userId);
}

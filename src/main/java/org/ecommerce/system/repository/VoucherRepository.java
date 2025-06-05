package org.ecommerce.system.repository;

import org.ecommerce.system.domain.entity.VoucherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long> {

    Optional<VoucherEntity> findByCode(String code);

    @Query("SELECT v FROM VoucherEntity v WHERE v.statusVoucher = 0 AND v.startDate <= :now AND v.endDate >= :now AND v.quantity > 0")
    List<VoucherEntity> findActiveVouchers(@Param("now") LocalDateTime now);

    @Query("SELECT v FROM VoucherEntity v WHERE v.statusVoucher = 0")
    Page<VoucherEntity> findAllActive(Pageable pageable);

    boolean existsByCode(String code);
}

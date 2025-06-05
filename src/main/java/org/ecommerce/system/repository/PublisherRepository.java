package org.ecommerce.system.repository;


import org.ecommerce.system.domain.entity.PublisherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Integer> {
    Optional<PublisherEntity> findByName(String name);

    List<PublisherEntity> findByIsActive(Integer isActive);

    @Query("SELECT p FROM PublisherEntity p WHERE p.isActive = 0")
    Page<PublisherEntity> findByIsActiveWithPagination(Pageable pageable);

    @Query("SELECT p FROM PublisherEntity p WHERE p.isActive = 0")
    Page<PublisherEntity> findAll(Pageable pageable);

    @Query("SELECT p FROM PublisherEntity p WHERE p.name LIKE %:name% AND p.isActive = 0")
    Page<PublisherEntity> findByNameContainingAndActive(@Param("name") String name, Pageable pageable);

    Optional<PublisherEntity> findById(Long id);

    List<PublisherEntity> findByNameContainingIgnoreCaseAndIsActive(String name, Integer isActive);

    @Query("SELECT COUNT(p) > 0 FROM PublisherEntity p WHERE LOWER(p.name) = LOWER(:name) AND p.id != :id")
    boolean existsByNameIgnoreCaseAndIdNot(@Param("name") String name, @Param("id") Long id);

    boolean existsByNameIgnoreCase(String name);
}

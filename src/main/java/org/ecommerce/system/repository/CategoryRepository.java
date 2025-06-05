package org.ecommerce.system.repository;


import jakarta.persistence.Tuple;
import org.ecommerce.system.domain.entity.CategoryEntity;
import org.ecommerce.system.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findById(Long id);

    List<CategoryEntity> findByIsActive(Integer isActive);


    @Query("SELECT c.id as id, c.name as name, c.description as description, " +
            "c.isActive as isActive, c.createdAt as createdAt " +
            "FROM CategoryEntity c " +
            "WHERE c.isActive = :isActive " +
            "ORDER BY c.name ASC")
    List<Tuple> findCategoriesByStatus(@Param("isActive") Integer isActive);

    @Query("SELECT c.id as categoryId, c.name as categoryName, " +
            "c.description as description, " +
            "COUNT(pc.id) as productCount " +
            "FROM CategoryEntity c " +
            "LEFT JOIN c.productCategories pc " +
            "WHERE c.isActive = 0 " +
            "GROUP BY c.id, c.name, c.description " +
            "ORDER BY COUNT(pc.id) DESC")
    List<Tuple> findTopCategoriesByProductCount();

    
}

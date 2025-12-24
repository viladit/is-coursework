package com.example.is_curse_work.repository;

import com.example.is_curse_work.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            select p from Product p
            join fetch p.owner o
            join fetch p.zone z
            join fetch z.fridge f
            where (:ownerEmail is null or lower(o.email) like lower(concat('%', :ownerEmail, '%')))
              and (:status is null or p.status = :status)
              and (:fridgeId is null or f.fridgeId = :fridgeId)
            order by p.placedAt desc
            """)
    List<Product> searchAdmin(@Param("ownerEmail") String ownerEmail,
                              @Param("status") String status,
                              @Param("fridgeId") Long fridgeId);

    long countByStatus(String status);
}

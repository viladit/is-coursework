package com.example.is_curse_work.repository;

import com.example.is_curse_work.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}


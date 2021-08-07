package com.product.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.catalog.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
	
}
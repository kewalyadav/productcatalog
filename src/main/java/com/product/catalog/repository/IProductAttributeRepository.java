package com.product.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.catalog.entity.ProductAttribute;

@Repository
public interface IProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
	
}
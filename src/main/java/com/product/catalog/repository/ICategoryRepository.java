package com.product.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.catalog.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
	
}
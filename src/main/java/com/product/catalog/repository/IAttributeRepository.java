package com.product.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.catalog.entity.Attribute;

@Repository
public interface IAttributeRepository extends JpaRepository<Attribute, Long> {
	
}
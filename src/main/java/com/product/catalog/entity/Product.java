package com.product.catalog.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue
	private Long productId;

	private String productName;

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProductAttribute> productAttribute;
}

package com.product.catalog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class ProductAttribute {

	@Id
    @GeneratedValue
    private Long productAttributeId;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="attributeId")
    @JsonManagedReference
    private Attribute attribute;
    
    @Column(name = "attribute_value")
    private String attributeValue;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="product_id")
    @JsonBackReference
    private Product product;
}

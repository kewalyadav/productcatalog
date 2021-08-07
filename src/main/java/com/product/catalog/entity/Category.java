package com.product.catalog.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class Category {
	
	@Id
    @GeneratedValue
    private Long categoryId;

    private String categoryName;
    
    @OneToMany(mappedBy="category", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Attribute> attributes;
}

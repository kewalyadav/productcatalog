package com.product.catalog.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CategoryDTO {
	
	@NotEmpty
    private String categoryName;
}

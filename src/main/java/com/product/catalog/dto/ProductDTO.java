package com.product.catalog.dto;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductDTO {
	
	@NotBlank
	private String productName;
	
	@NotNull
	private Long categoryId;
	
	private Map<Long, String> attributes;
}

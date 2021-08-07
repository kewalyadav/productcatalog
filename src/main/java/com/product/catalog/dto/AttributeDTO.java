package com.product.catalog.dto;

import java.util.List;

import lombok.Data;

@Data
public class AttributeDTO {
	private Long categoryId;

	private List<String> attributes;
}

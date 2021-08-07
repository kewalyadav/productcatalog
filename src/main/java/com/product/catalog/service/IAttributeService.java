package com.product.catalog.service;

import com.product.catalog.dto.AttributeDTO;
import com.product.catalog.entity.Category;

public interface IAttributeService {
	public Category addCategoryAttributes(AttributeDTO attributeDTO);
}

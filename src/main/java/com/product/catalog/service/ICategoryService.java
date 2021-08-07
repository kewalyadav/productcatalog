package com.product.catalog.service;

import com.product.catalog.dto.CategoryDTO;
import com.product.catalog.entity.Category;

public interface ICategoryService {
	public Category addCategory(CategoryDTO categoryDTO);

	public Category findCategoryById(Long categoryId);
}

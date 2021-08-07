package com.product.catalog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.catalog.dto.CategoryDTO;
import com.product.catalog.entity.Category;
import com.product.catalog.exception.InvalidDataException;
import com.product.catalog.repository.ICategoryRepository;
import com.product.catalog.service.ICategoryService;
import com.product.catalog.util.MessageUtil;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;
    
    @Autowired
    private MessageUtil messageUtil;

    @Override
	@Transactional
	public Category addCategory(CategoryDTO categoryDTO) {
    	return categoryRepository.save(Category.builder().categoryName(categoryDTO.getCategoryName()).build());
	}
    
	@Override
    public Category findCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId)
				  .orElseThrow(() -> new InvalidDataException(messageUtil.getMessage("invalid.category", categoryId)));
	}
}

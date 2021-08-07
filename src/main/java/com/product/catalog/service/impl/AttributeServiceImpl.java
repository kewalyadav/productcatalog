package com.product.catalog.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.catalog.dto.AttributeDTO;
import com.product.catalog.entity.Attribute;
import com.product.catalog.entity.Category;
import com.product.catalog.exception.InvalidDataException;
import com.product.catalog.repository.IAttributeRepository;
import com.product.catalog.service.IAttributeService;
import com.product.catalog.service.ICategoryService;
import com.product.catalog.util.MessageUtil;

@Service
public class AttributeServiceImpl implements IAttributeService {

    @Autowired
    private IAttributeRepository attributeRepository;
    
    @Autowired
    private ICategoryService categoryService;
    
    @Autowired
    private MessageUtil messageUtil;

	@Override
	@Transactional
	public Category addCategoryAttributes(AttributeDTO attributeDTO) {
    	Long categoryId = attributeDTO.getCategoryId();
    	Category category = categoryService.findCategoryById(categoryId);
    	List<String> attrname = category.getAttributes().stream().map(t -> t.getAttributeName()).collect(Collectors.toList());
    	if (Collections.disjoint(attrname, attributeDTO.getAttributes())) {
    		Set<Attribute> attrs = attributeDTO.getAttributes().stream()
					.map(t -> Attribute.builder().category(category).attributeName(t).build())
					.collect(Collectors.toSet());
			attributeRepository.saveAll(attrs);
			return categoryService.findCategoryById(categoryId);
    	} else {
    		throw new InvalidDataException(messageUtil.getMessage("attribute.already.exists", attrname, categoryId));
    	}
	}
}

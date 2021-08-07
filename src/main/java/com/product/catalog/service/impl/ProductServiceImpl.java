package com.product.catalog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.product.catalog.dto.ProductDTO;
import com.product.catalog.entity.Category;
import com.product.catalog.entity.Product;
import com.product.catalog.entity.ProductAttribute;
import com.product.catalog.exception.InvalidDataException;
import com.product.catalog.repository.IAttributeRepository;
import com.product.catalog.repository.ICategoryRepository;
import com.product.catalog.repository.IProductAttributeRepository;
import com.product.catalog.repository.IProductRepository;
import com.product.catalog.service.IProductService;
import com.product.catalog.util.MessageUtil;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;
    
    @Autowired
    private IProductAttributeRepository productAttributeRepository;
    
    @Autowired
    private IAttributeRepository attributeRepository;
    
    @Autowired
    private ICategoryRepository categoryRepository;
    
    @Autowired
    private MessageUtil messageUtil;

    @Override
	@Transactional
	public Product addProduct(ProductDTO productDTO) throws InvalidDataException {
    	Long categoryId = productDTO.getCategoryId();
    	Category category = categoryRepository.findById(categoryId)
						  .orElseThrow(() -> new InvalidDataException(messageUtil.getMessage("invalid.category", categoryId)));
    	List<Long> attrIds = category.getAttributes().stream()
									.map(t -> t.getAttributeId())
									.collect(Collectors.toList());
    	Map<Long, String> attrsFromRequest = productDTO.getAttributes();
    	List<Long> attrIdsFromRequest = new ArrayList<>(attrsFromRequest.keySet());
    	if(attrsFromRequest.values().stream().anyMatch(t -> ObjectUtils.isEmpty(t))) {
    		throw new InvalidDataException(messageUtil.getMessage("invalid.product.attribute.value", "Empty or Null"));
    	}
    	if(Objects.equals(attrIds, attrIdsFromRequest)){
    		return saveProduct(productDTO, category, attrIds, attrsFromRequest);
    	} else {
    		throw new InvalidDataException(messageUtil.getMessage("invalid.product.attributes", attrIds, categoryId));
    	}
	}

	private Product saveProduct(ProductDTO productDTO, Category category, List<Long> attrIds, 
								Map<Long, String> attrsFromRequest) {
		Long categoryId = productDTO.getCategoryId();
		Product product = productRepository.save(Product.builder().productName(productDTO.getProductName())
										    					  .category(category).build());
		List<ProductAttribute> productAttribute = productAttributeRepository.saveAll(
													attrIds.stream().map(t -> 
														ProductAttribute.builder()
														.product(product)
														.attribute(attributeRepository.findById(t).orElseThrow(
																() -> new InvalidDataException(messageUtil.getMessage("invalid.attribute", categoryId))))
														.attributeValue(attrsFromRequest.get(t).toString()).build())
													.collect(Collectors.toList()));
		return CollectionUtils.isEmpty(productAttribute) ? product : productAttribute.get(0).getProduct();
	}

	@Override
	public Product getProduct(Long productId) throws InvalidDataException {
		return productRepository.findById(productId).orElseThrow(() -> new InvalidDataException(messageUtil.getMessage("invalid.product", productId)));
	}
}

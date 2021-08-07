package com.product.catalog.service;

import com.product.catalog.dto.ProductDTO;
import com.product.catalog.entity.Product;
import com.product.catalog.exception.InvalidDataException;

public interface IProductService {
	public Product addProduct(ProductDTO productDTO) throws InvalidDataException;
	
	public Product getProduct(Long productId) throws InvalidDataException;
}

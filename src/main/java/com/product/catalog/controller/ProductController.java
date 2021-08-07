package com.product.catalog.controller;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.catalog.dto.ProductDTO;
import com.product.catalog.entity.Product;
import com.product.catalog.exception.InvalidDataException;
import com.product.catalog.model.DataResponse;
import com.product.catalog.model.GenericResponse;
import com.product.catalog.model.ResponseStatus;
import com.product.catalog.service.IProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")
@Api("Product Catalog Service")
public class ProductController {
	
	@Autowired
    private IProductService productService;
	
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Save Product in Cataglog", notes = "Save Product in Cataglog")
    public ResponseEntity<GenericResponse<DataResponse<Product>>> addProduct(@Valid @RequestBody ProductDTO productDTO) throws InvalidDataException {
    	return new ResponseEntity<>(GenericResponse.<DataResponse<Product>>builder()
								    			   .items(DataResponse.<Product>builder().data(productService.addProduct(productDTO)).build())
								    			   .statusCode(OK.value())
								    			   .status(ResponseStatus.SUCCESS).build(), OK);
    }
    
    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Get Product by Id From Cataglog", notes = "Get Product by Id From Cataglog")
    public ResponseEntity<GenericResponse<DataResponse<Product>>> getProduct(@Valid @PathVariable long productId) throws InvalidDataException {
    	return new ResponseEntity<>(GenericResponse.<DataResponse<Product>>builder()
								    			   .items(DataResponse.<Product>builder().data(productService.getProduct(productId)).build())
								    			   .statusCode(OK.value())
								    			   .status(ResponseStatus.SUCCESS).build(), OK);
    }
}

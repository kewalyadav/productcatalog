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

import com.product.catalog.dto.CategoryDTO;
import com.product.catalog.entity.Category;
import com.product.catalog.exception.InvalidDataException;
import com.product.catalog.model.DataResponse;
import com.product.catalog.model.GenericResponse;
import com.product.catalog.model.ResponseStatus;
import com.product.catalog.service.ICategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/category")
@Api("Product Category Service")
public class CategoryController {
	
    @Autowired
    private ICategoryService categoryService;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Save Product Category", notes = "Save Product Category")
    public ResponseEntity<GenericResponse<DataResponse<Category>>> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    	return new ResponseEntity<>(GenericResponse.<DataResponse<Category>>builder()
								    			   .items(DataResponse.<Category>builder().data(categoryService.addCategory(categoryDTO)).build())
								    			   .statusCode(OK.value())
								    			   .status(ResponseStatus.SUCCESS).build(), OK);
    }
    
    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Get Attributes by Category Id", notes = "Get Attributes by Category Id")
    public ResponseEntity<GenericResponse<DataResponse<Category>>> getCategoryAttributes(@Valid @PathVariable long categoryId) throws InvalidDataException {
    	return new ResponseEntity<>(GenericResponse.<DataResponse<Category>>builder()
								    			   .items(DataResponse.<Category>builder().data(categoryService.findCategoryById(categoryId)).build())
								    			   .statusCode(OK.value())
								    			   .status(ResponseStatus.SUCCESS).build(), OK);
    }
}

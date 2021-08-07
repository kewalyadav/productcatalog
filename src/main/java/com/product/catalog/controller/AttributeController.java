package com.product.catalog.controller;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.catalog.dto.AttributeDTO;
import com.product.catalog.entity.Category;
import com.product.catalog.model.DataResponse;
import com.product.catalog.model.GenericResponse;
import com.product.catalog.model.ResponseStatus;
import com.product.catalog.service.IAttributeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/attribute")
@Api("Product Category Attribute Service")
public class AttributeController {
	
    @Autowired
    private IAttributeService attributeService;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Save New Category Attribute", notes = "Save New Category Attribute")
    public ResponseEntity<GenericResponse<DataResponse<Category>>> addCategoryAttribute(@Valid @RequestBody AttributeDTO attributeDTO) {
    	return new ResponseEntity<>(GenericResponse.<DataResponse<Category>>builder()
								    			   .items(DataResponse.<Category>builder().data(attributeService.addCategoryAttributes(attributeDTO)).build())
								    			   .statusCode(OK.value())
								    			   .status(ResponseStatus.SUCCESS).build(), OK);
    }
}

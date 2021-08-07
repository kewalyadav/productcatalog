package com.product.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
@JsonInclude(Include.NON_NULL)
public class GenericResponse<T> {
    private ResponseStatus status;
    private Integer statusCode;
    private String message;
    private T items;
}
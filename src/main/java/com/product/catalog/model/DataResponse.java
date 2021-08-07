package com.product.catalog.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
@JsonInclude(Include.NON_NULL)
public class DataResponse<T> {
	private final String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS Z")); 
    private ResponseStatus status;
    private Integer statusCode;
    private String message;
    private List<String> errors;
    private T data;
}
package com.product.catalog.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 2314633642096422328L;
	
	private final String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }
}

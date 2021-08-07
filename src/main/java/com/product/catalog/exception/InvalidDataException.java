package com.product.catalog.exception;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidDataException extends CustomException {

	private static final long serialVersionUID = -1348116159119019350L;

	public InvalidDataException(String message) {
        super(message);
    }
	
	public InvalidDataException(List<String> messages) {
        super(messages.stream().collect(Collectors.joining(",")));
    }
}

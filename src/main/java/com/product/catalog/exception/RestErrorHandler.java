package com.product.catalog.exception;

import static com.product.catalog.model.ResponseStatus.FAILURE;
import static com.product.catalog.model.ResponseStatus.SUCCESS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.product.catalog.model.DataResponse;
import com.product.catalog.model.GenericResponse;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<Object> handleWebExchangeBindException(WebExchangeBindException ex, ServerWebExchange exchange) {
    	
    	final HttpStatus status = ex.getStatus();
		final List<String> errors = new ArrayList<>();
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getDefaultMessage());
		}
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		return new ResponseEntity<>(GenericResponse.builder().items(DataResponse.builder().statusCode(BAD_REQUEST.value())
				.status(FAILURE).message(status.getReasonPhrase())
				.errors(errors).build()).status(SUCCESS).statusCode(OK.value()).build(), OK);
	}

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Object> handleCustomException(final CustomException ex, WebRequest webRequest) {
        return new ResponseEntity<>(GenericResponse.builder().status(FAILURE).message(ex.getLocalizedMessage()).status(FAILURE).statusCode(OK.value()).build(), OK);
    }
    
    // 400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return handleExceptionInternal(ex, 
        		GenericResponse.builder()
        		.items(DataResponse.builder().statusCode(BAD_REQUEST.value()).status(FAILURE).message(status.getReasonPhrase()).errors(errors).build())
        		.status(SUCCESS)
        		.statusCode(OK.value()).build(), headers, OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        
        return handleExceptionInternal(ex, GenericResponse.builder()
        		.items(DataResponse.builder().statusCode(BAD_REQUEST.value()).status(FAILURE).message(status.getReasonPhrase()).errors(errors).build())
        		.status(SUCCESS)
        		.statusCode(OK.value()).build(), headers, OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        
        return handleExceptionInternal(ex, GenericResponse.builder()
        		.items(DataResponse.builder().statusCode(BAD_REQUEST.value()).status(FAILURE).message(status.getReasonPhrase()).errors(Collections.singletonList(error)).build())
        		.status(SUCCESS)
        		.statusCode(OK.value()).build(), headers, OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = ex.getRequestPartName() + " part is missing";
        
        return handleExceptionInternal(ex, GenericResponse.builder()
        		.items(DataResponse.builder().statusCode(BAD_REQUEST.value()).status(FAILURE).message(status.getReasonPhrase()).errors(Collections.singletonList(error)).build())
        		.status(SUCCESS)
        		.statusCode(OK.value()).build(), headers, OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = ex.getParameterName() + " parameter is missing";
        
        return handleExceptionInternal(ex, GenericResponse.builder()
        		.items(DataResponse.builder().statusCode(BAD_REQUEST.value()).status(FAILURE).message(status.getReasonPhrase()).errors(Collections.singletonList(error)).build())
        		.status(SUCCESS)
        		.statusCode(OK.value()).build(), headers, OK, request);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        return handleExceptionInternal(ex, GenericResponse.builder()
        		.items(DataResponse.builder().statusCode(BAD_REQUEST.value()).status(FAILURE).message(ex.getLocalizedMessage()).errors(Collections.singletonList(error)).build())
        		.status(SUCCESS)
        		.statusCode(OK.value()).build(), new HttpHeaders(), OK, request);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        
        return handleExceptionInternal(ex, GenericResponse.builder()
        		.items(DataResponse.builder().statusCode(BAD_REQUEST.value()).status(FAILURE).message(ex.getLocalizedMessage()).errors(errors).build())
        		.status(SUCCESS)
        		.statusCode(OK.value()).build(), new HttpHeaders(), OK, request);
    }
    
    // 400
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<String> errors = new ArrayList<>();
		if (ex.getCause() instanceof UnrecognizedPropertyException) {
			UnrecognizedPropertyException e = (UnrecognizedPropertyException) ex.getCause();
			String path = String.join(" > ", e.getPath().stream().map(t -> t.getFieldName()).collect(Collectors.toList()));
			errors.add(path + ": " + e.getPropertyName()+" is not a valid property.");
			return handleExceptionInternal(ex, GenericResponse.builder()
	        		.items(DataResponse.builder().errors(errors).build())
					.statusCode(BAD_REQUEST.value())
					.status(FAILURE).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		} else if(ex.getCause() instanceof JsonParseException) { 
			return handleExceptionInternal(ex, GenericResponse.builder()
	        		.items(DataResponse.builder().errors(Arrays.asList("Malformed JSON request")).build())
					.statusCode(BAD_REQUEST.value())
					.status(FAILURE).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		} else if (ex.getCause() instanceof InvalidTypeIdException) {
			InvalidTypeIdException e = (InvalidTypeIdException) ex.getCause();
			String originalMessage = e.getOriginalMessage();
			String message = "Field value is required";
			if (originalMessage.toLowerCase().contains("known type ids =")) {
				String knownTypesIds = originalMessage.substring(originalMessage.lastIndexOf("known type ids =") + ("known type ids =").length());
				message += " anyone from" + knownTypesIds;
			}
			errors.add("modelId : " +message+ ".");
			return handleExceptionInternal(ex, GenericResponse.builder()
	        		.items(DataResponse.builder().errors(errors).build())
					.statusCode(BAD_REQUEST.value())
					.status(FAILURE).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		} else if (ex.getCause() instanceof JsonMappingException) {
			JsonMappingException e = (JsonMappingException) ex.getCause();
			String path = String.join(" > ", e.getPath().stream().map(t -> t.getFieldName()).collect(Collectors.toList()));
			errors.add(path + ": " + e.getOriginalMessage());
			return handleExceptionInternal(ex, GenericResponse.builder()
	        		.items(DataResponse.builder().errors(errors).build())
					.statusCode(BAD_REQUEST.value())
					.status(FAILURE).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		} else if (!(ex.getCause() instanceof JsonMappingException)) {
			return handleExceptionInternal(ex, GenericResponse.builder()
	        		.items(DataResponse.builder().errors(Collections.singletonList(ex.getLocalizedMessage())).build())
					.statusCode(BAD_REQUEST.value())
					.status(FAILURE).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		}
        return handleExceptionInternal(ex, GenericResponse.builder()
        		.items(DataResponse.builder().errors(Collections.singletonList(ex.getLocalizedMessage())).build())
				.statusCode(BAD_REQUEST.value())
				.status(FAILURE).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        return handleExceptionInternal(ex, GenericResponse.builder()
        		.items(DataResponse.builder().statusCode(NOT_FOUND.value()).status(FAILURE).message(ex.getLocalizedMessage()).errors(Collections.singletonList(error)).build())
        		.status(FAILURE)
        		.statusCode(NOT_FOUND.value()).build(), new HttpHeaders(), NOT_FOUND, request);
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        builder.append(ex.getSupportedHttpMethods().toString());
        
        return handleExceptionInternal(ex, GenericResponse.builder()
        		.status(FAILURE)
        		.message(builder.toString())
        		.statusCode(METHOD_NOT_ALLOWED.value()).build(), new HttpHeaders(), METHOD_NOT_ALLOWED, request);
    }

    // 415
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ObjectUtils.isEmpty(ex.getContentType()) ? "Empty" : ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        builder.append(ex.getSupportedMediaTypes().toString());
        
        return handleExceptionInternal(ex, GenericResponse.builder()
        		.status(FAILURE)
        		.message(builder.toString())
        		.statusCode(UNSUPPORTED_MEDIA_TYPE.value()).build(), new HttpHeaders(), UNSUPPORTED_MEDIA_TYPE, request);
    }
    
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleException(final RuntimeException ex, ServerWebExchange exchange) {
    	logger.error("Error found: ", ex);
    	
    	return new ResponseEntity<>(GenericResponse.builder()
    			.message("Runtime Internal Server Error")
    			.items(DataResponse.builder().statusCode(INTERNAL_SERVER_ERROR.value()).status(FAILURE).message(ex.getCause().getLocalizedMessage()).build())
        		.status(FAILURE)
        		.statusCode(INTERNAL_SERVER_ERROR.value()).build(), new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(Exception.class)
	public ResponseEntity<GenericResponse<Object>> handleAllException(Exception ex, ServerWebExchange exchange) {
    	logger.error("Error found: ", ex);
		
		return new ResponseEntity<>(GenericResponse.builder()
				.message("Internal Server Error")
				.items(DataResponse.builder().statusCode(INTERNAL_SERVER_ERROR.value()).status(FAILURE).message(ex.getCause().getLocalizedMessage()).build())
        		.status(FAILURE)
        		.statusCode(INTERNAL_SERVER_ERROR.value()).build(), new HttpHeaders(), INTERNAL_SERVER_ERROR);
	}
}

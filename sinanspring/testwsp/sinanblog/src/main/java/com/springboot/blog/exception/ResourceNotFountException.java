package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFountException extends RuntimeException{
	private String resourceName;
	private String fieldName;
	private Long fieldValue;
	
	
	public ResourceNotFountException(String resourceName, String fieldName, Long fieldValue) {
		super(String.format("%s Not found with %s : '%s'",resourceName, fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public Long getFieldValue() {
		return fieldValue;
	}


	public void setFieldValue(Long fieldValue) {
		this.fieldValue = fieldValue;
	}


	

}

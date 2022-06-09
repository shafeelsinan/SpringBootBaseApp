package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{

	private HttpStatus httpStatus;
	private String messege;
	
	
	public BlogApiException(HttpStatus httpStatus, String messege) {
		this.httpStatus = httpStatus;
		this.messege = messege;
	}
	public BlogApiException(HttpStatus httpStatus, String messege,String messge1) {
		super(messege);
		this.httpStatus = httpStatus;
		this.messege = messge1;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessege() {
		return messege;
	}
	public void setMessege(String messege) {
		this.messege = messege;
	}
	
	
}
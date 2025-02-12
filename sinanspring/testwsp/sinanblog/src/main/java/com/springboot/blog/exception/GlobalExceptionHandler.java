package com.springboot.blog.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.springboot.blog.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {

	//specific exception and global exception
	@ExceptionHandler(ResourceNotFountException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFountException exception,WebRequest webrequest)
	{
		ErrorDetails errorDet = new ErrorDetails(new Date(), exception.getMessage(), webrequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDet,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogApiException.class)
	public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception,WebRequest webrequest)
	{
		ErrorDetails errorDet = new ErrorDetails(new Date(), exception.getMessage(), webrequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDet,HttpStatus.BAD_REQUEST);
	}
	
	//other exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleException(Exception exception,WebRequest webrequest)
	{
		ErrorDetails errorDet = new ErrorDetails(new Date(), exception.getMessage(), webrequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDet,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}

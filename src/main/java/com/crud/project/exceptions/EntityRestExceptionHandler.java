package com.crud.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityRestExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<RestErrorResponse> handleException(EntityNotFoundException exc)
	{
		RestErrorResponse response = new RestErrorResponse();
		response.setMessage(exc.getMessage());
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setTimestamps(System.currentTimeMillis());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		
	}
	
}

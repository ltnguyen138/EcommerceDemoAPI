package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


public class ErrorResponse {
	private HttpStatus status;
	private String message;
	
	public ErrorResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public ErrorResponse() {
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	 
	
	 
}

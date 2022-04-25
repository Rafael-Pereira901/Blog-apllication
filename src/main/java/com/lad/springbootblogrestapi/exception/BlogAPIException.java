package com.lad.springbootblogrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BlogAPIException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private HttpStatus status;
	
	public BlogAPIException(String message,HttpStatus status) {
		this.message = message;
		this.status = status;
	}
	
	public BlogAPIException(String message, HttpStatus status, String message1) {
		super(message);
		this.status = status;
		this.message = message;
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

package com.lad.springbootblogrestapi.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
	private long id;
	
	@NotEmpty(message = "name should not be null or empty")
	private String name;
	
	@Email(message = "sobuld be a valid email")
	@NotEmpty(message = "email should not be null or empty")
	private String email;
	
	@NotEmpty
	@Size(min = 10, message = "Comment body must be minium 10 characters")
	private String body;
}

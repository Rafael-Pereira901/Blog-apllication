package com.lad.springbootblogrestapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lad.springbootblogrestapi.payload.CommentDto;
import com.lad.springbootblogrestapi.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	private CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") long postId){
		return new ResponseEntity<List<CommentDto>>(commentService.getCommentsByPostId(postId), HttpStatus.OK) ;
	}
	
	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(
			@PathVariable(name = "postId")  Long postId, 
			@PathVariable(name = "id") Long commentId)
	{
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<CommentDto>(commentDto,HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(
			@PathVariable(name = "postId") long postId,
			@PathVariable(name = "id") long commentId, 
			@Valid @RequestBody CommentDto commentDto)
	{
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return new ResponseEntity<CommentDto>(updatedComment,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(
			@PathVariable(name = "postId")  Long postId, 
			@PathVariable(name = "id") Long commentId)
	{
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<String>("Comment deleted sucessfully.", HttpStatus.OK);
	}
}

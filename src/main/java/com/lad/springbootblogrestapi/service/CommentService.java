package com.lad.springbootblogrestapi.service;

import java.util.List;

import com.lad.springbootblogrestapi.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(long postId, long commentId);
	
	CommentDto updateComment(Long postId, long commentId, CommentDto commentResquest);
	
	void deleteComment(Long postId, Long commentId);
	
}

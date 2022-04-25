package com.lad.springbootblogrestapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lad.springbootblogrestapi.entity.Comment;
import com.lad.springbootblogrestapi.entity.Post;
import com.lad.springbootblogrestapi.exception.BlogAPIException;
import com.lad.springbootblogrestapi.exception.ResourceNotFoundException;
import com.lad.springbootblogrestapi.payload.CommentDto;
import com.lad.springbootblogrestapi.repository.CommentRepository;
import com.lad.springbootblogrestapi.repository.PostRepository;
import com.lad.springbootblogrestapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);
		
		//retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		comment.setPost(post);
		
		// comment entity to DB
		Comment newComment = commentRepository.save(comment);
		
		return mapToDto(newComment);
	}
	
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comments by postId
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}
	
	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		
		Comment comment = retrieveAndValidateComment(postId, commentId);
		
		return mapToDto(comment);
	}
	
	@Override
	public CommentDto updateComment(Long postId, long commentId, CommentDto commentResquest) {
		
		Comment comment = retrieveAndValidateComment(postId, commentId);
		
		comment.setName(commentResquest.getName());
		comment.setEmail(commentResquest.getEmail());
		comment.setBody(commentResquest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		return mapToDto(updatedComment);
	}
	
	@Override
	public void deleteComment(Long postId, Long commentId) {

		Comment comment = retrieveAndValidateComment(postId, commentId);
		
		commentRepository.delete(comment);
	}
	
	private Comment retrieveAndValidateComment(Long postId, Long commentId) {
		// retrieve post by id
		Post post = postRepository.findById(postId).orElseThrow(() 
				-> new ResourceNotFoundException("Post", "id", postId));
		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() 
				-> new ResourceNotFoundException("Comment", "id", commentId));
		
		validateRelation(post, comment);
		
		return comment;
		
	}
	
	private void validateRelation(Post post, Comment comment) {
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException("Commment does not belong to post", HttpStatus.BAD_REQUEST);
		}		
	}
	
	private CommentDto mapToDto(Comment comment) {
		return mapper.map(comment, CommentDto.class);
	}
	
	private Comment mapToEntity(CommentDto commentDto) {
		return mapper.map(commentDto, Comment.class);
	}




}

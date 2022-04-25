package com.lad.springbootblogrestapi.service;

import com.lad.springbootblogrestapi.payload.PostDto;
import com.lad.springbootblogrestapi.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDto getPostByid(Long id);
	
	PostDto updatePost(PostDto dto, Long id);
	
	void deletePostById(Long id);
	
}

package com.lad.springbootblogrestapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lad.springbootblogrestapi.entity.Post;
import com.lad.springbootblogrestapi.exception.ResourceNotFoundException;
import com.lad.springbootblogrestapi.payload.PostDto;
import com.lad.springbootblogrestapi.payload.PostResponse;
import com.lad.springbootblogrestapi.repository.PostRepository;
import com.lad.springbootblogrestapi.service.PostService;

@Service
public class PostServiceImpl  implements PostService{

	private PostRepository postRepository;
	private ModelMapper mapper;
	
	@Autowired // tendo um unico construtor isso se torna desnecessário
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}
	
	@Override
	public PostDto createPost(PostDto postDto) {
		Post post = mapToEntity(postDto);
		Post newPost = postRepository.save(post);
		PostDto postResponse = mapToDTO(newPost);
		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		//create Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		// get content from page object
		List<Post> listOfPost = posts.getContent();
		
		List<PostDto> content = listOfPost.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
	
	@Override
	public PostDto getPostByid(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}

	@Override
	public PostDto updatePost(PostDto dto, Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(dto.getTitle());
		post.setDescription(dto.getDescription());
		post.setContent(dto.getContent());
		return mapToDTO(postRepository.save(post));
	}

	@Override
	public void deletePostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}
	
	// Convert Entity to Dto
	private PostDto mapToDTO(Post post) {
		return mapper.map(post, PostDto.class);
	}
	
	// Convert Dto to Entity
	private Post mapToEntity(PostDto dto) {
//		Post post = new Post();
//		post.setTitle(dto.getTitle());
//		post.setDescription(dto.getDescription());
//		post.setContent(dto.getContent());
//		return post;
		return mapper.map(dto, Post.class);
	}
}

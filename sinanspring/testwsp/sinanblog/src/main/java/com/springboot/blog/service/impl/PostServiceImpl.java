package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.domain.Post;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}


	public PostRepository getPostRepository() {
		return postRepository;
	}


	public void setPostRepository(PostRepository postRepository) {
		this.postRepository = postRepository;
	}


	@Override
	public PostDto createPost(PostDto postDto)
	{
		//convert dto to entity
		Post post = dtoTOobj(postDto);
		Post newpost = postRepository.save(post);
		
		//convert entitty to dto
		PostDto postreponse = mapToDto(post);
		
		return postreponse;
	}


	@Override
	public PostResponse getAllPost(int pageNo,int pageSize,String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageble = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> posts = postRepository.findAll(pageble);
		// get Content From page Object
		List<Post> listPost = posts.getContent();
		List<PostDto> content = listPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		PostResponse response = new PostResponse();
		response.setContent(content);
		response.setPageNo(posts.getNumber());
		response.setPageSize(posts.getSize());
		response.setTotalElement(posts.getTotalElements());
		response.setTotalPages(posts.getTotalPages());
		response.setLast(posts.isLast());
		
		return response;
	}
	
	private PostDto mapToDto(Post post)
	{
		PostDto postDto = mapper.map(post, PostDto.class);
//		PostDto postreponse = new PostDto();
//		postreponse.setId(post.getId());
//		postreponse.setDescription(post.getDescription());
//		postreponse.setTitle(post.getTitle());
//		postreponse.setContent(post.getContent());
		return postDto;
	}
	
	private Post dtoTOobj(PostDto postDto)
	{
		Post post = mapper.map(postDto, Post.class);
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
	}


	@Override
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("post", "id", id));
		return mapToDto(post);
	}


	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		//get post by id from datatbase
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("post", "id", id));
		
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		
		Post updatedpost = postRepository.save(post);
		return mapToDto(updatedpost);
	}


	@Override
	public void deletePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("post", "id", id));
		postRepository.delete(post);
	}

}

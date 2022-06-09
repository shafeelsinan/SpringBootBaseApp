package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto);
	PostResponse getAllPost(int pageNo,int pageSize,String sortBy,String sortDir);
	PostDto getPostById(Long id);
	PostDto updatePost(PostDto postDto,Long id);
	void deletePost(Long id);
}

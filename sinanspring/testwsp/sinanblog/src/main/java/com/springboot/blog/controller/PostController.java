package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postservice;

	public PostController(PostService postservice) {
		this.postservice = postservice;
	}
	
//	create post api
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto)
	{
		return new ResponseEntity<>(postservice.createPost(postDto),HttpStatus.CREATED);
	}
	
	//get all post rest api
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
		return new ResponseEntity<PostResponse>(postservice.getAllPost(pageNo, pageSize, sortBy,sortDir), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id) {
		return ResponseEntity.ok(postservice.getPostById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable(name = "id")Long id)
	{
		PostDto postResponse =  postservice.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name="id") Long id)
	{
		postservice.deletePost(id);
		return new ResponseEntity<>("Post Entity Deleted Successfully",HttpStatus.OK);
	}
	
	
}

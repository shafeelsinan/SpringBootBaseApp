package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId,
			@RequestBody CommentDto commnetDto) {
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commnetDto), HttpStatus.CREATED);
	}

	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
		return commentService.getCommentsByPostId(postId);
	}

	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getComponentById(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "id") Long commnetId) {
		return new ResponseEntity<CommentDto>(commentService.getCommnetById(postId, commnetId), HttpStatus.OK);
	}

	@PutMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateCommnet(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "id") Long commnetId, @RequestBody CommentDto commnetDto) {
		CommentDto commentsDto = commentService.updateCommnet(postId, commnetId, commnetDto);
		return new ResponseEntity<CommentDto>(commentsDto,HttpStatus.OK);
	}

}

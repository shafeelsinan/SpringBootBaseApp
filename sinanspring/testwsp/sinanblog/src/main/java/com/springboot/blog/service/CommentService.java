package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.CommentDto;

public interface CommentService {

	CommentDto createComment(Long postId,CommentDto commnetDto);
	
	List<CommentDto> getCommentsByPostId(Long postId);
	
	CommentDto getCommnetById(Long postId,Long commnetId);
	
	CommentDto updateCommnet(Long postId,Long commnetId,CommentDto commnetDto);
}

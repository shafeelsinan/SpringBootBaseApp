package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.domain.Post;
import com.springboot.blog.dto.Comment;
import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commnetRepository;

	@Autowired
	PostRepository postRepository;

	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commnetRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commnetRepository = commnetRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(Long postId, CommentDto commnetDto) {
		Comment comment = mapToEntity(commnetDto);

		// retriveve post Entity
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFountException("post", "id", postId));

		comment.setPost(post);

		// save Comment entity
		Comment newCommnet = commnetRepository.save(comment);
		return mapToDto(comment);
	}

	private CommentDto mapToDto(Comment comment) {
		CommentDto commnetDto = mapper.map(comment, CommentDto.class);
//		commnetDto.setId(comment.getId());
//		commnetDto.setBody(comment.getBody());
//		commnetDto.setEmail(comment.getEmail());
//		commnetDto.setName(comment.getName());

		return commnetDto;
	}

	private Comment mapToEntity(CommentDto commnetDto) {
		Comment commnet = mapper.map(commnetDto, Comment.class);
//		commnet.setId(commnetDto.getId());
//		commnet.setBody(commnetDto.getBody());
//		commnet.setEmail(commnetDto.getEmail());
//		commnet.setName(commnetDto.getName());

		return commnet;
	}

	@Override
	public List<CommentDto> getCommentsByPostId(Long postId) {
		// Retrive Commnet By postId
		List<Comment> comments = commnetRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommnetById(Long postId, Long commnetId) {
		// Retrive Commnet By postId and post id
		// retriveve post Entity
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFountException("post", "id", postId));

		// retrive commnet by id
		Comment commnet = commnetRepository.findById(commnetId)
				.orElseThrow(() -> new ResourceNotFountException("Commnet", "id", commnetId));

		if (!commnet.getPost().getId().equals(postId)) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Commnet Does Not Belong To The Post");
		}

		return mapToDto(commnet);
	}

	@Override
	public CommentDto updateCommnet(Long postId, Long commnetId, CommentDto commnetDto) {
		// update Commnets
		// retriveve post Entity
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFountException("post", "id", postId));

		// retrive commnet by id
		Comment comment = commnetRepository.findById(commnetId)
				.orElseThrow(() -> new ResourceNotFountException("Commnet", "id", commnetId));

		if (!comment.getPost().getId().equals(postId)) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Commnet Does Not Belong To The Post");
		}

		comment.setBody(commnetDto.getBody());
		comment.setEmail(commnetDto.getEmail());
		comment.setName(commnetDto.getName());

		Comment updatedCommnet = commnetRepository.save(comment);

		return mapToDto(updatedCommnet);
	}

}

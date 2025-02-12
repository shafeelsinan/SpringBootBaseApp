package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.dto.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByPostId(Long postId);
}

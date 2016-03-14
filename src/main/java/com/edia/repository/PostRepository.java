package com.edia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edia.domain.Post;

/**
 * Repository for post
 * 
 * @author magdisz
 *
 */
public interface PostRepository extends JpaRepository<Post, Long> {

	/**
	 * Return all posts
	 */
	public List<Post> findAll();

	/**
	 * Return all posts ordered by created date
	 * 
	 * @return
	 */
	public List<Post> findAllByOrderByCreatedDateAsc();

}

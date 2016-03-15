package com.edia.service;

import java.util.List;

import com.edia.domain.Post;

/**
 * Service to handle Posts
 * 
 * @author szmagda
 *
 */
public interface PostService {

	/**
	 * Save post to data store
	 * 
	 * @param post
	 */
	void addPost(Post post);

	/**
	 * Find post which text's contain the specified searchWord
	 * 
	 * @param searchWord
	 * @return
	 */
	List<Post> searchByTextContaining(String searchWord);

	/**
	 * Find all the posts
	 * 
	 * @return
	 */
	List<Post> findAll();

}

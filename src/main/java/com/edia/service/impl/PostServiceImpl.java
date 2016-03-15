package com.edia.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edia.domain.Post;
import com.edia.repository.PostRepository;
import com.edia.service.ElasticsearchService;
import com.edia.service.PostService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class PostServiceImpl implements PostService {

	private final String POST_INDEX = "post";
	private final String TYPE = "text";

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ElasticsearchService elasticsearchService;

	@Override
	public void addPost(Post post) {
		post = postRepository.save(post);
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		String jsonPost = gson.toJson(post);
		elasticsearchService.addDocument(POST_INDEX, TYPE, post.getId(), jsonPost);
	}

	@Override
	public List<Post> searchByTextContaining(String searchWord) {
		List<String> result = elasticsearchService.multiMatchSearch(searchWord, "createdDate", SortOrder.ASC,
				POST_INDEX, TYPE);
		Gson gson = new Gson();
		List<Post> posts = new ArrayList<>();
		for (String post : result) {
			posts.add(gson.fromJson(post, Post.class));
		}
		return posts;
	}

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

}

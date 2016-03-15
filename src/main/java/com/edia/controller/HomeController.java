package com.edia.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.edia.domain.Post;
import com.edia.service.PostService;

/**
 * Main page controller
 * 
 * @author magdisz
 *
 */
@Controller
public class HomeController {

	@Autowired
	private PostService postService;

	@RequestMapping("/")
	public String home(@RequestParam(name = "searchText", required = false) String searchText, Model model)
			throws Exception {
		List<Post> posts;
		if (searchText == null || searchText.isEmpty()) {
			posts = postService.findAll();
		} else {
			posts = postService.searchByTextContaining(searchText);
		}
		model.addAttribute("posts", posts);
		model.addAttribute("post", new Post());
		return "home";
	}

	/**
	 * Add new valid post
	 * 
	 * @param post
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String addPost(@Valid Post post, BindingResult result) {
		if (result.hasErrors())
			return "home";
		else {
			post.setCreatedDate(new Date());
			postService.addPost(post);
			return "redirect:/";
		}
	}

	/**
	 * Global error handling
	 * @param ex
	 * @param model
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, Model model) {
		model.addAttribute("error", ex.getMessage());
		return "home";
	}

}

package com.edia.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edia.domain.Post;
import com.edia.repository.PostRepository;

/**
 * Main page controller
 * 
 * @author magdisz
 *
 */
@Controller
public class HomeController {

	@Autowired
	private PostRepository postRepository;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("posts", postRepository.findAllByOrderByCreatedDateAsc());
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
			postRepository.save(post);
			return "redirect:/";
		}
	}
}

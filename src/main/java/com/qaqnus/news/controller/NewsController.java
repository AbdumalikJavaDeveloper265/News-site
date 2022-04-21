package com.qaqnus.news.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.qaqnus.news.entity.News;
import com.qaqnus.news.entity.User;
import com.qaqnus.news.repository.UserRepository;
import com.qaqnus.news.service.NewsService;
import com.qaqnus.news.service.UserService;

@Controller
public class NewsController {
	
	@Autowired
	NewsService newsService;
	@Autowired
	UserService userService;
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	@GetMapping("/news")
//	public String allNews(Model model) {
//		model.addAttribute("allNews", newsService.getAll());		
//		
//		return "news";
//	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	@GetMapping("/news/{id}")
	public String showNews(@PathVariable("id") Long id, Model model) {
		model.addAttribute("news", newsService.getNewsByIdIfExist(id));
		return "news";
	}
	@GetMapping("/userNewsList")
	public String userOwnNews(Model model, HttpServletRequest request) {
		model.addAttribute("userNewsList", userService.getUserByUsername(request.getUserPrincipal().getName()).getAllNews());
		return "user_own_news";
	}
	
	@GetMapping("/newsList")
	public String allNews(Model model, HttpServletRequest request) {
		String templateName = "news";
		if(newsService.getAll() == null) {
			return "no_news_found";
		}else if(newsService.getNewsListByApprovedTrue() == null){
			return "no_news_found";
		} else if(request.isUserInRole("ROLE_ADMIN")){
			model.addAttribute("approvedNewsList", newsService.getNewsListByApprovedTrue());
			model.addAttribute("noApprovedNewsList", newsService.getNewsListByApprovedFalse());
			templateName = "show_news_for_admin";
			 
		} else {
			model.addAttribute("allNews", newsService.getNewsListByApprovedTrue());
			templateName = "show_news_for_users"; 
		}
		
		
		return templateName;
	}
	
	@GetMapping("/addNews")
	public String getNews(Model model) {
		model.addAttribute("newsForm", new News());
		return "add_news";
	}
	
	@PostMapping("/addNews")
	public String addNews(@ModelAttribute("newsForm") @Valid News newsForm,BindingResult bindingResult, Model model, HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "add_news";
		}
		String name = request.getUserPrincipal().getName();
		newsForm.setUser(userService.getUserByUsername(name));
		
		if(request.isUserInRole("ROLE_NAME")) {
			newsForm.setIs_approved(true);
		}
		
		if(!newsService.saveNews(newsForm)) {
			model.addAttribute("error", "This operation isn't done!");
			return "redirect:addNews";
		}
		
		return "redirect:newsList";
	}
	// Updating news
	
	@GetMapping("/updateNews/{id}")
	public String getUpdatingPage(@PathVariable("id") Long id , Model model) {
		News news = newsService.getNewsByIdIfExist(id);
		
		model.addAttribute("newsForm", news);
		return "update_news";
	}
	
	@PostMapping("/updateNews/{id}")
	public String postUpdatingPage(@PathVariable("id") Long news_id, @ModelAttribute("newsForm") @Valid News newsForm,BindingResult bindingResult, Model model, HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "update_news";
		}
		
		if(!newsService.updateNews(newsForm, news_id)) {
			model.addAttribute("error", "This operation isn't done!");
			return "redirect:addNews";
		}
		
		return "redirect:/newsList";
	}
	
	// EndUpdating news!

	
	@PostMapping("/approve/{id}")
	public String approve(@PathVariable("id") Long id, HttpServletRequest request) {
		News newsFromDb = newsService.getNewsByIdIfExist(id);
		newsFromDb.setIs_approved(true);
		
		newsService.saveNews(newsFromDb);
		return "redirect:/newsList";
	}
	
	

}

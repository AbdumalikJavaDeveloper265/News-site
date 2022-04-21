package com.qaqnus.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qaqnus.news.service.UserService;

@Controller
public class AdminController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin")
	public String userList(Model model) {
		model.addAttribute("allUsers", userService.allUsers());
		return "admin";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long userId){
		userService.deleteUser(userId);
		return "redirect:/admin";
	}
	
//	public String gtUser(@PathVariable("userId")Long userId, Model model) {
//		model.addAttribute("allUsers", userService.usergtList(userId));
//		return "admin";
//	}
	

}

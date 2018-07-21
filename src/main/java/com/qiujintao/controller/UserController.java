package com.qiujintao.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qiujintao.domain.User;
import com.qiujintao.service.UserService;

@Controller
@RequestMapping("/app")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@RequestMapping()
	@ResponseBody
	public Map<String, String> hello(){
		Map<String, String> map = new HashMap<>();
		map.put("hello", "world!");
		return map;
	}
	@GetMapping("create-user")
	public ModelAndView createUserView(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new User());
		mav.addObject("allProfiles", getProfiles());
		mav.setViewName("user-creation");
		return mav;
	}
	
	@PostMapping("create-user")
	public ModelAndView createUser(ModelAndView mav, @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			LOGGER.info("Validation errors while submitting form.");
			mav.setViewName("user-creation");
			mav.addObject("user", user);
			mav.addObject("allProfiles", getProfiles());
			return mav;
		}
		userService.addUser(user);
		mav.addObject("allUsers",userService.getAllUsers());
		mav.setViewName("user-info");
		LOGGER.info("Form submitted successfully");
		return mav;
	}
	
	private List<String> getProfiles(){
		List<String> list = new ArrayList<>();
		list.add("Developer");
		list.add("Manager");
		list.add("Director");
		return list;
	}
}

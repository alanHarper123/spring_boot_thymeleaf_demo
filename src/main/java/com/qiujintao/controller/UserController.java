package com.qiujintao.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
	public ModelAndView createUserView() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new User());
		mav.addObject("allProfiles", getProfiles());
		mav.setViewName("user-creation");
		return mav;
	}

	@PostMapping("create-user")
	public String createUser(Model mav,@Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			LOGGER.info("Validation errors while submitting form.");
			mav.addAttribute("allProfiles", getProfiles());
			return "user-creation";
		}
		userService.addUser(user);
		mav.addAttribute("allUsers",userService.getAllUsers());
		mav.addAttribute("userImge", getUserImage());
		LOGGER.info("Form submitted successfully");
		return "user-info";
	}

	private List<String> getProfiles(){
		List<String> list = new ArrayList<>();
		list.add("Developer");
		list.add("Manager");
		list.add("Director");
		return list;
	}
	private String getUserImage() {
		BufferedImage bufferedImage = null;
		byte[] imageByte = null;
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			bufferedImage = ImageIO.read(new File("F:\\codeJava\\tmall_ssm\\src\\main\\webapp\\img\\lunbo\\3.jpg"));
			ImageIO.write(bufferedImage, "jpg", bao);
			bao.flush();
			imageByte = bao.toByteArray();
			bao.close();
		} catch (Exception e) {
			System.out.println(new RuntimeException(e));
		}finally {

		}
		return Base64.getEncoder().encodeToString(imageByte);
	}
}

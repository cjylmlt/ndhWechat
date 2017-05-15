package com.cjy.myWeb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.myWeb.service.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	@RequestMapping("/register")
	public String register(HttpSession session,String username,String password,String checkCode,HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.out.println(username+password+checkCode);
		if(userService.checkCheckCode(request, response)&&userService.checkRegisterEmpty(request, response)){
			if(userService.registerNewUser(request, response)){
				session.setAttribute("username", request.getAttribute("username"));
				return "index";
			}
			else {
				return "registerFail";
			}
		}
		else
			return "registerFail";
	}
	@RequestMapping("/registerView")
	public ModelAndView registerView(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("register");
		return modelAndView;
	}
}

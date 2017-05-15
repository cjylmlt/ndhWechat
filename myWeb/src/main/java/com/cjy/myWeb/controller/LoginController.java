package com.cjy.myWeb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.myWeb.service.*;


@Controller
public class LoginController {
	@Autowired
    private UserService userService;
	@RequestMapping("/login")
	public ModelAndView login(HttpSession session,String username,String password,String checkCode,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		System.out.println(username+password+checkCode);
		if(userService.checkCheckCode(request, response)&&userService.checkLoginEmpty(request, response)){
			if(userService.checkLoginPassword(request, response)){
				request.getSession().setAttribute("userId", userService.findUserByUsername(username).getId());
				mav.setViewName("index");
				mav.addObject("loginUsername",username);
				mav.addObject("userImage",userService.findUserByUsername(username).getUserImg());
				return mav;
			}
			else {
				mav.setViewName("loginFail");
				String faiMessage = "账户名或者密码错误";
				mav.addObject("failMessage",faiMessage);
				return mav;
			}
		}
		else
			mav.setViewName("loginFail");
			String faiMessage = "账户名或者密码错误";
			mav.addObject("failMessage",faiMessage);
			return mav;
	}
	@RequestMapping("/loginView")
	public ModelAndView loginView(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
}

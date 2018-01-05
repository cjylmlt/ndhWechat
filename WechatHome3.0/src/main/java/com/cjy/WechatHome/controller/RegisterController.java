package com.cjy.WechatHome.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.service.UserService;

@Controller
public class RegisterController {
	@Autowired
	UserService userService;
	@Autowired
	HostHolder HostHolder;
	@RequestMapping(path={"/reg/"},method = {RequestMethod.POST})
	public String register(Model model,@RequestParam("username") String username,@RequestParam("wechatId") String wechatId,@RequestParam(value="next",required=false)String next,@RequestParam("password") String password,HttpServletResponse response){
		Map<String,String> map = userService.register(username, password,wechatId);
		if(map.containsKey("ticket")){
			Cookie cookie = new Cookie("ticket", map.get("ticket"));
			cookie.setPath("/");
			//response.addCookie(cookie);
			if(!StringUtils.isEmpty(next))
				return "redirect:"+next;
			else 
				return "redirect:/";
			
		}
		else{
			model.addAttribute("msg", map.get("msg"));
			return "login";
		}
	}
	@RequestMapping(path={"/register"},method = {RequestMethod.GET})
	public String goToregister(){
		return "register";
	}
	@RequestMapping(path={"/passwordSetting"},method = {RequestMethod.GET})
	public String goTopasswordSetting(Model model){
		User user = HostHolder.getUser();
		model.addAttribute("user", user);
		return "passwordSetting";
	}

	@RequestMapping(path={"/changePassword"},method = {RequestMethod.POST})
	public String changPassword(@RequestParam("userId")int userId,
			@RequestParam("oldPassword")String oldPassword,
			@RequestParam("newPassword")String newPassword,
			@RequestParam("confirmPassword")String confirmPassword,
			Model model){
		Map<String,String> map = userService.changePassword(userId, oldPassword, newPassword, confirmPassword);
		if(map.containsKey("ticket")){
			Cookie cookie = new Cookie("ticket", map.get("ticket"));
			cookie.setPath("/");
			//response.addCookie(cookie);
			return "redirect:/";
			
		}
		else{
			model.addAttribute("msg", map.get("msg"));
			return "passwordSetting";
		}
		
	}
	
}

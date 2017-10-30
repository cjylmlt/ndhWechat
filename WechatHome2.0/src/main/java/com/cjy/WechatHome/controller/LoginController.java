package com.cjy.WechatHome.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjy.WechatHome.dao.LoginTicketDao;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.service.UserService;

@Controller
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	LoginTicketDao ticketDao;
	@RequestMapping(path={"/login/"},method = {RequestMethod.POST})
	public String login(Model model,@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam(value="next",required=false)String next,HttpServletResponse response){
		Map<String,String> map = userService.login(username, password);
		if(map.containsKey("ticket")){
			Cookie cookie = new Cookie("ticket", map.get("ticket"));
			cookie.setPath("/");
			response.addCookie(cookie);
			if (!StringUtils.isEmpty(next)) {
				return "redirect:"+next;
			}
			else
				return "redirect:/";
		}
		else{
			model.addAttribute("msg", map.get("msg"));
			return "login";
		}
	}
	@RequestMapping(path={"/reglogin"},method = {RequestMethod.GET})
	public String reglogin(Model model,@RequestParam(value="next",required=false)String next){
		model.addAttribute("next",next);
		return "login";		
	}
	@RequestMapping(path={"/logout"},method = {RequestMethod.GET})
	public String reglogout(@CookieValue("ticket")String ticket){	
		userService.logout(ticket);
		return "redirect:/";
	}
	
	
}
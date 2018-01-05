package com.cjy.WechatHome.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.WechatHome.dao.SpiderWebDao;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.TopRecord;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.model.ViewObject;
import com.cjy.WechatHome.model.WechatUser;
import com.cjy.WechatHome.service.MessageService;
import com.cjy.WechatHome.service.RecordService;
import com.cjy.WechatHome.service.SpiderWebService;
import com.cjy.WechatHome.service.TopRecordService;
import com.cjy.WechatHome.service.UserService;
import com.cjy.WechatHome.spider.VideoSpider;
import com.cjy.WechatHome.util.WechatUtil;

@Controller
public class ProxyController {
	private static final Logger logger = LoggerFactory.getLogger(ProxyController.class);
	@Autowired
	VideoSpider videoSpider;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
	@RequestMapping(path={"search.php"})
	public String getSearch(Model model,@RequestParam("sousuo")String searchWord) {
		try {
			String urlContent = URLEncoder.encode(searchWord,"UTF-8");
			model.addAttribute("content", videoSpider.getSearchSource(urlContent));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "proxy";
	}
	@RequestMapping(path={"/dsj/**"},method = {RequestMethod.GET})
	public String getMovie(Model model,HttpServletRequest request) {
		String address = request.getRequestURI();
		WechatUser w = hostHolder.getWechatUser();
		User user;
		if(w==null)
			user = null;
		else
			user = userService.getUserByUserId(w.getBelongOwnerId());
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		model.addAttribute("wechatOwnerUser", hostHolder.getWechatOwnerUser());
		model.addAttribute("content", videoSpider.getMovieSource(address,user));
		if(hostHolder.getWechatUser()!=null)
			model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getWechatUser().getOpenId()));
		return "proxy";
	}
	@RequestMapping(path={"/play.php","/mplay.php"},method = {RequestMethod.GET})
	public String getPlay(Model model,HttpServletRequest request) {
		StringBuilder address = new StringBuilder();
		address.append(request.getRequestURI()).append("?");
		Map<String, String[]> paramMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {  
			if(entry.getKey().equals("make")||entry.getKey().equals("play")||entry.getKey().equals("mso"))
			address.append(entry.getKey()+"="+entry.getValue()[0]);
		}  
		WechatUser w = hostHolder.getWechatUser();
		User user;
		if(w==null)
			user = null;
		else
			user = userService.getUserByUserId(w.getBelongOwnerId());
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		model.addAttribute("wechatOwnerUser", hostHolder.getWechatOwnerUser());
		model.addAttribute("content", videoSpider.getMovieSource(address.toString(),user));
		if(hostHolder.getWechatUser()!=null)
		model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getWechatUser().getOpenId()));
		return "proxy";
	}
	@RequestMapping(path={"/jiazai.php"},method = {RequestMethod.GET})
	@ResponseBody
	public String getJiazai(HttpServletRequest request) {
		String address = request.getRequestURI();
		return videoSpider.getMovieSource(address,null);
	}
	@RequestMapping(path={"/css/*","/js/*","/images/*"},method = {RequestMethod.GET})
	@ResponseBody
	public String getCss(Model model,HttpServletRequest request) {
		String address = request.getRequestURI();
		return videoSpider.getMovieSource(address,null);
	}
	
}

package com.cjy.WechatHome.theater.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cjy.WechatHome.web.service.UserSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.WechatHome.spider.VideoSpider;
import com.cjy.wechathome.data.theater.Fan;
import com.cjy.WechatHome.theater.service.MessageService;
import com.cjy.WechatHome.data.HostHolder;
import com.cjy.wechathome.data.web.User;
import com.cjy.WechatHome.web.service.UserService;

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
	@Autowired
	UserSettingService userSettingService;
	@RequestMapping(path={"search.php"})
	public String getSearch(Model model,@RequestParam("sousuo")String searchWord) {
		try {
			String urlContent = URLEncoder.encode(searchWord,"UTF-8");
			model.addAttribute("content", videoSpider.getSearchSource(urlContent));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "theater/proxy";
	}
	@RequestMapping(path={"/dsj/**"},method = {RequestMethod.GET})
	public String getDsj(Model model,HttpServletRequest request) {
		String address = request.getRequestURI();
		User user;
		if(hostHolder.getFan()==null)
			user = null;
		else
			user = userService.getUserByUserId(hostHolder.getFan().getBelongOwnerId());
		model.addAttribute("wechatUser", hostHolder.getFan());
		model.addAttribute("wechatOwnerUser", hostHolder.getFanOwnerUser());
		model.addAttribute("content", videoSpider.getMovieSource(address,user));
		if(hostHolder.getFan()!=null)
			model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getFan().getOpenId()));
		return "theater/proxy";
	}
	
	@RequestMapping(path={"/movie.php","/tv.php","zongyi.php","dongman.php"},method = {RequestMethod.GET})
	public String getMovie(Model model,HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		String address = request.getRequestURI()+"?";
		for (Map.Entry<String, String[]> map : parameterMap.entrySet()) {
			address+=map.getKey();
			address+="=";
			address+=map.getValue()[0];
			address+="&";
		}
		User user;
		if(hostHolder.getFan()==null)
			user = null;
		else
			user = userService.getUserByUserId(hostHolder.getFan().getBelongOwnerId());
		model.addAttribute("wechatUser", hostHolder.getFan());
		model.addAttribute("wechatOwnerUser", hostHolder.getFanOwnerUser());
		model.addAttribute("content", videoSpider.getMovieSource(address,user));
		if(hostHolder.getFan()!=null)
			model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getFan().getOpenId()));
		return "theater/proxy";
	}
	
	@RequestMapping(path={"/v"},method = {RequestMethod.GET})
	public String getTheaterIndex(Model model,HttpServletRequest request) {
		long startTime = System.currentTimeMillis();
		User user;
		if(hostHolder.getFan()==null)
			user = null;
		else
			user = userService.getUserByUserId(hostHolder.getFan().getBelongOwnerId());
		model.addAttribute("wechatUser", hostHolder.getFan());
		model.addAttribute("wechatOwnerUser", hostHolder.getFanOwnerUser());
		model.addAttribute("content", videoSpider.getMovieSource("",user));
		if(hostHolder.getFan()!=null)
			model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getFan().getOpenId()));
		logger.info("controller="+(System.currentTimeMillis()-startTime)+"ms");
		return "theater/proxy";
	}
	
	@RequestMapping(path={"/play.php","/mplay.php"},method = {RequestMethod.GET})
	public String getPlay(Model model,HttpServletRequest request) {
//		String openId = WechatUtil.getUserOpenId(code);
//		String access_token = WechatUtil.getWebAccessToken(code);
//		WechatUser tempuser = WechatUtil.getWebWechatUser(access_token,openId);
		StringBuilder address = new StringBuilder();
		address.append(request.getRequestURI()).append("?");
		Map<String, String[]> paramMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {  
			if(entry.getKey().equals("make")||entry.getKey().equals("play")||entry.getKey().equals("mso"))
			address.append(entry.getKey()+"="+entry.getValue()[0]);
		}  
		Fan w = hostHolder.getFan();
		User user;
		if(w==null)
			user = null;
		else
			user = userService.getUserByUserId(w.getBelongOwnerId());
		model.addAttribute("wechatUser", hostHolder.getFan());
		model.addAttribute("wechatOwnerUser", hostHolder.getFanOwnerUser());
		model.addAttribute("content", videoSpider.getMovieSource(address.toString(),user));
		if(hostHolder.getFan()!=null)
			model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getFan().getOpenId()));
		if(hostHolder.getFan().getExpireTime().getTime()<new Date().getTime()+3600*1000*24*10){
			model.addAttribute("warnning","warnning");
		}
		return "theater/proxy";
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

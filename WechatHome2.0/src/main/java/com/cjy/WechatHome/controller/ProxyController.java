package com.cjy.WechatHome.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

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
	@RequestMapping(path={"/v"},method = {RequestMethod.GET})
	public String index(Model model,@RequestParam(value="code", required=false)String code,@RequestParam(value="state", required=false)String state) {
		String content = videoSpider.getIndexSource();
		content = content.replaceAll("<a target=\"_blank\" href=\" http://neihantutu.lofter.com/taobao\"><div class=\"code\"><span class=\"hd_waiting\">去拆红包</span></div></a>", "");
		content = content.replaceAll("<header[\\s\\S]+?</header>", "");
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		model.addAttribute("wechatOwnerUser", hostHolder.getWechatOwnerUser());
		model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getWechatUser().getOpenId()));
		model.addAttribute("content", content);
		return "proxy";
	}
	
	@RequestMapping(path={"search.php"})
	public String getSearch(Model model,@RequestParam("searchword")String searchWord) {
		try {
			String urlContent = URLEncoder.encode(searchWord,"UTF-8");
			model.addAttribute("content", videoSpider.getSearchSource(urlContent));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "proxy";
	}
	@RequestMapping(path={"/movie/{index}"},method = {RequestMethod.GET})
	public String getMovie(Model model,@PathVariable("index")String index) {
		String address = "movie/"+index+".html";
		WechatUser w = hostHolder.getWechatUser();
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		model.addAttribute("wechatOwnerUser", hostHolder.getWechatOwnerUser());
		model.addAttribute("content", videoSpider.getMovieSource(address));
		if(hostHolder.getWechatUser()!=null)
			model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getWechatUser().getOpenId()));
		return "proxy";
	}
	@RequestMapping(path={"/play/{index}"},method = {RequestMethod.GET})
	public String getPlay(Model model,@PathVariable("index")String index) {
		String address = "play/"+index+".html";
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		model.addAttribute("wechatOwnerUser", hostHolder.getWechatOwnerUser());
		model.addAttribute("content", videoSpider.getMovieSource(address));
		if(hostHolder.getWechatUser()!=null)
		model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getWechatUser().getOpenId()));
		return "proxy";
	}
	@RequestMapping(path={"/include/{index}"},method = {RequestMethod.GET})
	public String getInclude(Model model,
			@PathVariable("index")String index,
			@RequestParam("action")String action,
			@RequestParam("id")int id,
			@RequestParam("timestamp")long timestamp) {
		String address = "include/"+index+".php?"+"action="+action+"&&id="+id+"&&timestamp="+timestamp;
//		return videoSpider.getMovieSource(address);
		model.addAttribute("content", videoSpider.getMovieSource(address));
		return "proxy";
	}
	@RequestMapping(path={"/js/player/{videoSource}"},method = {RequestMethod.GET})
	public String getJs(@PathVariable("videoSource")String videoSource){
		if("papapa".equals(videoSource)||"youku".equals(videoSource)||"mgtv".equals(videoSource)||"yibuyun".equals(videoSource))
			return videoSource;
		else{
			return "iqiyi";
		}
	}
	@RequestMapping(path={"api.yibuyy.com/tz/youku.php"},method = {RequestMethod.GET})
	public String getYouku(Model model){
		String address = "";
		model.addAttribute("content", videoSpider.getMovieSource(address));
		return "proxy";
	}
	@RequestMapping(path={"/youkuproxy"},method = {RequestMethod.GET})
	@ResponseBody
	public String getYoukuProxy(Model model,@RequestParam("key")String key){
		String a = videoSpider.getYoukuSource(key);
		a = a.replace("https://api.yibuyy.com/youkuyun/index.php?id=", "http://api.baiyug.cn/vip/index.php?url="); 
		return a ;
	}
	@RequestMapping(path={"/Iqiyiproxy"},method = {RequestMethod.GET})
	@ResponseBody
	public String getIqiyiProxy(Model model,@RequestParam("key")String key){
		String a = videoSpider.getYoukuSource(key);
		return a ;
	}
	@RequestMapping(path={"/papapaproxy"},method = {RequestMethod.GET})
	@ResponseBody
	public String getPapapaProxy(Model model,@RequestParam("key")String key){
		String a = videoSpider.getPapapaSource(key);
		return a ;
	}
	@RequestMapping(path={"/qqproxy"},method = {RequestMethod.GET})
	@ResponseBody
	public String getQqProxy(Model model,@RequestParam("key")String key){
		String a = videoSpider.getQqSource(key);
		String b = videoSpider.sendGet(a);
		return b ;
	}
	@RequestMapping(path={"/yibuyunProxy"},method = {RequestMethod.GET})
	@ResponseBody
	public String getYibuyunProxy(Model model,@RequestParam("key")String key){
		return videoSpider.sendGetForyYibuyun(key);
	}
	
	@RequestMapping(path={"/ser/search"},method = {RequestMethod.GET})
	@ResponseBody
	public String getSearchProxy(Model model){
		String source = videoSpider.sendGet("http://www.byjsj.cn/ser/search.html");
		source = source.replace("fonts/font-awesome-4.2.0/css/font-awesome.min.css", "http://www.byjsj.cn/ser/fonts/font-awesome-4.2.0/css/font-awesome.min.css");
		source = source.replace("css/normalize.css", "http://www.byjsj.cn/ser/css/normalize.css");
		source = source.replace("css/demo.css", "http://www.byjsj.cn/ser/css/demo.css");
		source = source.replace("css/component.css", "http://www.byjsj.cn/ser/css/component.css");
		return source ;
	}
	@RequestMapping(path={"/ser/js/classie.js"},method = {RequestMethod.GET})
	public String getClassieProxy(Model model){
		return "redirect:http://www.byjsj.cn/ser/js/classie.js";
	}
}

package com.cjy.WechatHome.web.controller;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.WechatHome.dao.AdminSettingDao;
import com.cjy.WechatHome.dao.NewsDao;
import com.cjy.WechatHome.theater.model.Fan;
import com.cjy.WechatHome.theater.service.FanService;
import com.cjy.WechatHome.theater.service.SpiderWebService;
import com.cjy.WechatHome.web.model.HostHolder;
import com.cjy.WechatHome.web.model.User;
import com.cjy.WechatHome.web.model.WechatStatics;
import com.cjy.WechatHome.web.service.WechatStaticsService;

@Controller
public class AdminSettingController {
	@Autowired
	AdminSettingDao adminSettingDao;
	
	@Autowired
	WechatStaticsService wechatStaticsService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	SpiderWebService spiderWebService;
	@Autowired
	FanService fanService;
	@Autowired
	NewsDao newsDao;
	private static final Logger logger = LoggerFactory.getLogger(AdminSettingController.class);
	@RequestMapping(path={"/adminSetting"},method = {RequestMethod.POST})
	public String changeHostWeb(@RequestParam("hostWeb")String hostWeb,Model model){
		adminSettingDao.updateAdminSetting("hostWeb", hostWeb);
		return "redirect:/";
	}
	@RequestMapping(path={"/adminSetting/changeWebUrl"},method = {RequestMethod.POST})
	public String changeWebUrl(@RequestParam("webUrl")String webUrl,Model model){
		int id = Integer.valueOf(adminSettingDao.selectAdminSetting("hostWeb"));
		spiderWebService.updateWebUrl(webUrl, id);
		newsDao.deleteNewsAll();
		return "redirect:/";
	}
	@RequestMapping(path={"/wechatStatics"},method = {RequestMethod.POST,RequestMethod.GET})
	public String wechatStatics(Model model){
		User visitUser = hostHolder.getUser();
		if(visitUser.getUsername().equals("admin")){
			model.addAttribute("wechatStaticsList",wechatStaticsService.selectFirstPage());
			model.addAttribute("startDate",wechatStaticsService.selectStartDate());
			model.addAttribute("endDate",wechatStaticsService.selectEndDate());
		}
		return "wechat";
	}
	@RequestMapping(path={"/adminSetting/addWechatStatics"},method = {RequestMethod.POST})
	public String addStatics(Model model,@RequestParam("statics")int statics){
		Random random = new Random();
		int newUser = statics;
		int cancelUser = (int) (random.nextInt(statics)*0.09+statics*0.09);
	    int netgainUser = newUser - cancelUser;
		int cumulateUser = wechatStaticsService.selectFirstPage().get(0).getCumulateUser()+netgainUser;
		WechatStatics wechatStatics = new WechatStatics();
		wechatStatics.setCancelUser(cancelUser);
		wechatStatics.setCumulateUser(cumulateUser);
		wechatStatics.setDate(new Date(wechatStaticsService.selectEndDate().getTime()+24*3600*1000));
		wechatStatics.setNetgainUser(netgainUser);
		wechatStatics.setNewUser(newUser);
		wechatStaticsService.insertStatics(wechatStatics);
		return "redirect:/wechatStatics";
	}
	@RequestMapping(path={"/search"},method = {RequestMethod.POST,RequestMethod.GET})
	public String searchWechatUser(Model model,@RequestParam("q")String username){
		User user = hostHolder.getUser();
		model.addAttribute("user",user);
		Fan wechatUser = fanService.selectFanByName(username);
		model.addAttribute("wechatUser", wechatUser);
		return "wechatUser";
	}
	@RequestMapping(path={"/wechatUser/delete"},method = {RequestMethod.POST,RequestMethod.GET})
	public String deleteWechatUser(Model model,@RequestParam("userName")String username){
		fanService.deleteFan(username);
		return "redirect:/search?q="+username;
	}
}
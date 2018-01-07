package com.cjy.WechatHome.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cjy.WechatHome.theater.service.SpiderWebService;
import com.cjy.WechatHome.web.model.HostHolder;
import com.cjy.WechatHome.web.service.TopRecordService;
import com.cjy.WechatHome.web.service.UserService;

@Controller
public class HomePageController {
	private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
	@Autowired
	UserService userService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	TopRecordService topRecordService;
	@Autowired
	SpiderWebService spiderWebService;
	@RequestMapping(path={"/","/index"},method = {RequestMethod.GET})
	public String index(Model model){
		model.addAttribute("topRecordList", topRecordService.selectTopRecord()); 
		model.addAttribute("subRecord", topRecordService.selectSubRecord("subscribe"));
		model.addAttribute("unSubRecord", topRecordService.selectSubRecord("unSubscribe"));
		model.addAttribute("choosenWeb", spiderWebService.selectChoosenSpiderWeb().getWebName());
		model.addAttribute("oldWebUrl", spiderWebService.selectChoosenSpiderWeb().getWebUrl());
		if(hostHolder.getUser()!=null&&hostHolder.getUser().getUsername().equals("admin")){
			model.addAttribute("userList", userService.selectAll());
			model.addAttribute("hostWebList",spiderWebService.selectSpiderWebList());
		}
		return "index";
	}

	
}

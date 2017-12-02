package com.cjy.WechatHome.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjy.WechatHome.dao.AdminSettingDao;
import com.cjy.WechatHome.dao.SpiderWebDao;


@Controller
public class AdminSettingController {
	@Autowired
	AdminSettingDao adminSettingDao;
	@Autowired
	HomeController homeController;
	private static final Logger logger = LoggerFactory.getLogger(AdminSettingController.class);
	@RequestMapping(path={"/adminSetting"},method = {RequestMethod.POST})
	public String changeHostWeb(@RequestParam("hostWeb")String hostWeb,Model model){
		adminSettingDao.updateAdminSetting("hostWeb", hostWeb);
		return "redirect:/";
	}
	@RequestMapping(path={"/changeStatics"},method = {RequestMethod.POST,RequestMethod.GET})
	public String changeStatics(Model model){
		return "wechat";
	}
	
}

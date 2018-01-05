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
public class CompanyController {
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	@RequestMapping(path={"/company/index","/company","/company/"},method = {RequestMethod.GET})
	public String index(){
		return "company/index";
	}
	@RequestMapping(path={"/company/about-us"},method = {RequestMethod.GET})
	public String aboutUs(){
		return "company/about-us";
	}
	@RequestMapping(path={"/company/services"},method = {RequestMethod.GET})
	public String services(){
		return "company/services";
	}
	@RequestMapping(path={"/company/blog"},method = {RequestMethod.GET})
	public String blog(){
		return "company/blog";
	}
	@RequestMapping(path={"/company/contact-us"},method = {RequestMethod.GET})
	public String contactUus(){
		return "company/contact-us";
	}
}

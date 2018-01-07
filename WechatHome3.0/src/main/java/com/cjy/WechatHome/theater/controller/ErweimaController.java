package com.cjy.WechatHome.theater.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErweimaController {
	private static final Logger logger = LoggerFactory.getLogger(ErweimaController.class);
	@RequestMapping(path={"/WechatHome/app/erweima"},method = {RequestMethod.GET})
	public String erweima(Model model){
		String myUrl = "http://www.baidu.com";
		model.addAttribute("myUrl",myUrl);
		return "app/erweima";
	}
}

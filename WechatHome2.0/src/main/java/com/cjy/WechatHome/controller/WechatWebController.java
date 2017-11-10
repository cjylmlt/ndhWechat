package com.cjy.WechatHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WechatWebController {
	@RequestMapping("/expireWarning")
	public String expireWarning(){
		return "expireWarning";
	}
}

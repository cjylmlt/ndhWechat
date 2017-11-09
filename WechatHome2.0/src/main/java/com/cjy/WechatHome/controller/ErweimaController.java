package com.cjy.WechatHome.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.TopRecord;
import com.cjy.WechatHome.model.ViewObject;
import com.cjy.WechatHome.service.RecordService;
import com.cjy.WechatHome.service.TopRecordService;
import com.cjy.WechatHome.service.UserService;

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

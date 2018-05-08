package com.cjy.WechatHome.web.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.async.EventType;
import com.cjy.wechathome.data.theater.Fan;
import com.cjy.wechathome.data.theater.FanPo;
import com.cjy.WechatHome.theater.service.FanService;
import com.cjy.WechatHome.data.HostHolder;
import com.cjy.wechathome.data.web.User;
import com.cjy.WechatHome.web.service.DefinedReplyService;
import com.cjy.WechatHome.web.service.UserService;

@Controller
public class FansController {
	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	@Autowired
	DefinedReplyService definedReplyService;
	@Autowired
	FanService fanService;
	@Autowired
	EventProducer eventProducer;
	@RequestMapping(path = { "/user/{id}/fans/statics" }, method = { RequestMethod.GET })
	@ResponseBody
	public String getFansStatics(@PathVariable("id") int id,Model model) {
		User visitUser = hostHolder.getUser();
		if(visitUser.getId()==id||visitUser.getUsername().equals("admin")){
			return fanService.getFanPoJsonString(id);
		}
		return "";
	}
	@RequestMapping(path = { "/user/{id}/fans" }, method = { RequestMethod.GET })
	public String getFans(@PathVariable("id") int id,Model model) {
		User visitUser = hostHolder.getUser();
		User user;
		if (visitUser == null) {
			return "/userSetting";
		}
		if ("admin".equals(visitUser.getUsername())) {
			user = userService.getUser(id);
			int fansNum = fanService.selectFansCountByOwner(user.getUserId());
			model.addAttribute("settingUser", user);
			model.addAttribute("adminUser", visitUser);
			model.addAttribute("fansNum",fansNum);
			List<Fan> fansList = fanService.selectFansByOwner(user.getUserId(), 0, 100000);
			List<FanPo> fanPoList = new LinkedList<>();
			for (Fan fan : fansList) {
				FanPo fanPo = new FanPo(fan);
				if(new Date().before(fan.getExpireTime())){
					fanPo.setIsExpired("正常");
				}
				else{
					fanPo.setIsExpired("过期");
				}
				fanPoList.add(fanPo);
			}
			model.addAttribute("fans",fanPoList);
		} else {
			user = visitUser;
			int fansNum = fanService.selectFansCountByOwner(user.getUserId());
			model.addAttribute("settingUser", user);
			model.addAttribute("adminUser", null);
			model.addAttribute("fansNum",fansNum);
			
			List<Fan> fansList = fanService.selectFansByOwner(user.getUserId(), 0, 100000);
			List<FanPo> fanPoList = new LinkedList<>();
			for (Fan fan : fansList) {
				FanPo fanPo = new FanPo(fan);
				if(new Date().before(fan.getExpireTime())){
					fanPo.setIsExpired("正常");
				}
				else{
					fanPo.setIsExpired("过期");
				}
				fanPoList.add(fanPo);
			}
			model.addAttribute("fans",fanPoList);
		}
		return "fans";
	}
	@RequestMapping(path = { "/user/{id}/fans/renewall" }, method = { RequestMethod.POST })
	public String renewAllFans(@PathVariable("id") int id,@RequestParam("days") int days,Model model) {
		User visitUser = hostHolder.getUser();
		User user;
		if (visitUser == null) {
			return "/userSetting";
		}
		if ("admin".equals(visitUser.getUsername())||visitUser.getId()==id) {
			user = userService.getUser(id);
			EventModel eventModel = new EventModel(EventType.RENEWVIP);
			eventModel.setExt("openId", user.getUserId());
			eventModel.setExt("range", "all");
			eventModel.setExt("days", String.valueOf(days));
			eventProducer.fireEvent(eventModel);
		}
		return "redirect:/user/"+id;
	}
	
	@RequestMapping(path = { "/user/{id}/fans/renewexpired" }, method = { RequestMethod.POST })
	public String renewExpiredFans(@PathVariable("id") int id,@RequestParam("days") int days,Model model) {
		User visitUser = hostHolder.getUser();
		User user;
		if (visitUser == null) {
			return "/userSetting";
		}
		if ("admin".equals(visitUser.getUsername())||visitUser.getId()==id) {
			user = userService.getUser(id);
			EventModel eventModel = new EventModel(EventType.RENEWVIP);
			eventModel.setExt("openId", user.getUserId());
			eventModel.setExt("range", "expired");
			eventModel.setExt("days", String.valueOf(days));
			eventProducer.fireEvent(eventModel);
		}
		return "redirect:/user/"+id;
	}
	@RequestMapping(path = { "/user/{id}/fans/renew" }, method = { RequestMethod.POST })
	public String renewFan(@PathVariable("id") int id,
			@RequestParam("days") int days,
			@RequestParam("openId")String openId,
			Model model) {
		User visitUser = hostHolder.getUser();
		if (visitUser == null) {
			return "/userSetting";
		}
		if ("admin".equals(visitUser.getUsername())||visitUser.getId()==id) {
			fanService.updateFan(openId, days);
		}
		return "redirect:/user/"+id+"/fans";
	}
	@RequestMapping(path = { "/user/{id}/fans/delete" }, method = { RequestMethod.POST })
	public String deleteFan(@PathVariable("id") int id,
			@RequestParam("openId")String openId,
			Model model) {
		User visitUser = hostHolder.getUser();
		if (visitUser == null) {
			return "/userSetting";
		}
		if ("admin".equals(visitUser.getUsername())||visitUser.getId()==id) {
			fanService.deleteFanById(openId);
		}
		return "redirect:/user/"+id+"/fans";
	}
}

package com.cjy.WechatHome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjy.WechatHome.model.DefinedReply;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.service.DefinedReplyService;
import com.cjy.WechatHome.service.UserService;

@Controller
public class SettingController {
	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	@Autowired
	DefinedReplyService definedReplyService;

	@RequestMapping(path = { "/user/{id}" }, method = { RequestMethod.GET })
	public String getUserSetting(@PathVariable("id") int id, Model model) {
		User visitUser = hostHolder.getUser();
		User user;
		DefinedReply ad = null;
		List<DefinedReply> definedReplyList;
		if (visitUser == null) {
			return "userSetting";
		}
		if ("admin".equals(visitUser.getUsername())) {
			user = userService.getUser(id);
			definedReplyList = definedReplyService.getReplyByUser(user.getUsername());
			model.addAttribute("settingUser", user);
			model.addAttribute("adminUser", visitUser);
		} else {
			user = visitUser;
			definedReplyList = definedReplyService.getReplyByUser(user.getUsername());
			model.addAttribute("settingUser", user);
			model.addAttribute("adminUser", null);
		}
		try {
			List<DefinedReply> adList = definedReplyService.getADList(user.getUsername());
			if (adList.size() > 0) {
				ad = adList.get(0);
			}
			model.addAttribute("ad", ad);
			model.addAttribute("definedReplyList", definedReplyList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userSetting";
	}

	@RequestMapping(path = { "/userSetting" }, method = { RequestMethod.POST })
	public String setUserSetting(@RequestParam("id") int id, @RequestParam("username") String username,
			@RequestParam("wechatId") String userId, @RequestParam("status") int status,
			@RequestParam("chineseName") String chineseName, @RequestParam("noteOne") String noteOne,
			@RequestParam("headUrl") String headUrl, Model model) {
		User visitUser = hostHolder.getUser();
		User user;
		DefinedReply ad = null;
		List<DefinedReply> definedReplyList = null;
		if (visitUser.getUsername().equals("admin")) {
			user = userService.getUser(id);
			user.setUsername(username);
			user.setUserId(userId);
			user.setStatus(status);
			user.setChineseName(chineseName);
			user.setHeadUrl(headUrl);
			user.setNoteOne(noteOne);
			userService.updateUser(user);
		} else {
			user = visitUser;
		}
		try {
			List<DefinedReply> adList = definedReplyService.getADList(user.getUsername());
			if (adList.size() > 0) {
				ad = adList.get(0);
			}
			model.addAttribute("ad", ad);
			model.addAttribute("definedReplyList", definedReplyList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/" + String.valueOf(id);
	}


}

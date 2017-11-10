package com.cjy.WechatHome.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.model.WechatUser;
import com.cjy.WechatHome.service.UserService;
import com.cjy.WechatHome.service.WechatUserService;
import com.cjy.WechatHome.util.WechatUtil;

@Controller
public class WechatWebController {
	private static final String HOST_URL = "http://fortestwechat.free.ngrok.cc";
	@Autowired
	HostHolder hostHolder;
	@Autowired
	WechatUserService wechatUserService;
	@Autowired
	UserService userService;
	@RequestMapping("/expireWarning")
	public String expireWarning(Model model,@RequestParam("next")String next){
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		hostHolder.clearWechatUser();
		model.addAttribute("next", next);
		return "expireWarning";
	}
	@RequestMapping("/userInfo")
	public String getUserInfo(Model model){
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		model.addAttribute("wechatOwnerUser", hostHolder.getWechatOwnerUser());
		String targetUrl = HOST_URL+"/subscribe?introducerId="+hostHolder.getWechatUser().getOpenId();
		String packUrl = WechatUtil.packWebUrl(targetUrl);
		try {
			packUrl = URLEncoder.encode(packUrl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String qrCode = "http://qr.topscan.com/api.php?text="+ packUrl;
		model.addAttribute("qrCode", qrCode);
		hostHolder.clearWechatUser();
		return "userInfo";
	}
	@RequestMapping("/subscribe")
	public String getSubInfo(Model model,@RequestParam("introducerId")String introducerId,HttpServletRequest request){
		//判断是否为有效Id
		if(wechatUserService.isWchatUserExist(introducerId)){
			//判断当前账号是否注册过
			String code = request.getParameter("code");
			String openId = WechatUtil.getUserOpenId(code);
			if(!openId.equals("")){
				if(wechatUserService.isWchatUserExist(openId)){
					//如果存在，则推荐无效
				}
				else{
	
					
					WechatUser introduceUser = wechatUserService.selectWechatUser(introducerId);
					introduceUser.setExpireTime(new Date(introduceUser.getExpireTime().getTime()+3600*1000*24));
					wechatUserService.updateExpireTime(introduceUser);
					//找到owner
					User owner = userService.getUserByUserId(introduceUser.getBelongOwnerId());
					model.addAttribute("owner", owner);
					
					//注册当前用户
					WechatUser nweWechatUser = new WechatUser();
					nweWechatUser.setOpenId(openId);
					nweWechatUser.setBelongOwnerId(owner.getUserId());
					nweWechatUser.setUserName(UUID.randomUUID().toString().substring(0, 10));
					nweWechatUser.setExpireTime(new Date(new Date().getTime()+3600*1000*24*7));
					wechatUserService.regWechatUser(nweWechatUser);
					hostHolder.setWechatUser(nweWechatUser);
					hostHolder.setWechatOwnerUser(userService.getUserByUserId(nweWechatUser.getBelongOwnerId()));
					Cookie cookie = new Cookie("ticket", userService.addLoginTicket(nweWechatUser.getOpenId()));
					cookie.setPath("/");
					
					return "userOwnerInfo";
				}
			}
			else {
				return "error/error";
			}
		}
		return "/v";
	}
}

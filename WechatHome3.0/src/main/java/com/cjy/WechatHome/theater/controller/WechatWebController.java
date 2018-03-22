package com.cjy.WechatHome.theater.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjy.WechatHome.web.model.UserSetting;
import com.cjy.WechatHome.web.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.theater.model.Fan;
import com.cjy.WechatHome.theater.model.Message;
import com.cjy.WechatHome.theater.service.FanService;
import com.cjy.WechatHome.theater.service.MessageService;
import com.cjy.WechatHome.util.WechatUtil;
import com.cjy.WechatHome.web.model.HostHolder;
import com.cjy.WechatHome.web.model.User;
import com.cjy.WechatHome.web.service.UserService;

@Controller
public class WechatWebController {
	private static String HOST_URL;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	FanService fanService;
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	@Autowired
	EventProducer eventProducer;
	@Autowired
	UserSettingService userSettingService;
	@Value("${wechat.interceptor}")
	String interceptor;


	public WechatWebController(@Value("${host.url}")String host){
		HOST_URL = host;
	}
	@RequestMapping("/expireWarning")
	public String expireWarning(Model model,@RequestParam("userId")String userId){
		Fan fan = fanService.selectFan(userId);
		User wechatOwnerUser = userService.getUserByUserId(fan.getBelongOwnerId());
		model.addAttribute("wechatUser", fan);
		model.addAttribute("wechatOwnerUser", wechatOwnerUser);
		model.addAttribute("messageUnreadCount", messageService.getUnreadCount(fan.getOpenId()));
		String targetUrl = HOST_URL+"/subscribe?introducerId="+fan.getOpenId();
		String packUrl = WechatUtil.packWebUrl(targetUrl,"123");
		try {
			packUrl = URLEncoder.encode(packUrl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String qrCode = "http://qr.topscan.com/api.php?text="+ packUrl;
		model.addAttribute("qrCode", qrCode);
		return "theater/expireWarning";
	}
	@RequestMapping("/userInfo")
	public String getUserInfo(Model model){
		if(interceptor.equals("false")){
			model.addAttribute("fan",fanService.selectFan("oKtSQ07yrpPNKn2giRihih8XA6dg"));
			model.addAttribute("messageList", messageService.getMessageList(fanService.selectFan("oKtSQ07yrpPNKn2giRihih8XA6dg").getOpenId(), 0, 3));
			messageService.updateReadStatus(hostHolder.getFan().getOpenId());
		}
		else if(interceptor.equals("true")){
			model.addAttribute("fan", hostHolder.getFan());
			model.addAttribute("messageList", messageService.getMessageList(hostHolder.getFan().getOpenId(), 0, 3));
			messageService.updateReadStatus(hostHolder.getFan().getOpenId());
		}
		return "theater/fanpage";
	}
	
	@RequestMapping("/myQrCode")
	public String getQrcode(Model model){
		String targetUrl = HOST_URL+"/subscribe?introducerId="+hostHolder.getFan().getOpenId();
		String packUrl = WechatUtil.packWebUrl(targetUrl,"123");
		try {
			packUrl = URLEncoder.encode(packUrl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String qrCode = "http://qr.topscan.com/api.php?text="+ packUrl;
		model.addAttribute("qrCode", qrCode);
		return "theater/myQrCode";
	}
	@RequestMapping("/poster")
	public String getPoster(Model model,@RequestParam(value = "next",required = false)String next){
		model.addAttribute("next",next);
		return "theater/poster";
	}
	
	@RequestMapping("/subscribe")
	public String getSubInfo(Model model,@RequestParam("introducerId")String introducerId,HttpServletRequest request,HttpServletResponse response){
		//判断是否为有效Id
		if(fanService.isFanExist(introducerId)){
			Fan introduceFan = fanService.selectFan(introducerId);
			User owner = userService.getUserByUserId(introduceFan.getBelongOwnerId());
			model.addAttribute("owner", owner);
			//获取该owner的设置
			UserSetting userSetting = userSettingService.selectUserSetting(owner.getId());
			//判断当前账号是否注册过
			String code = request.getParameter("code");
			String openId = WechatUtil.getUserOpenId(code);
			if(openId!=null&&!openId.equals("")){
				if(fanService.isFanExist(openId)){
					Fan wechatUser = fanService.selectFan(openId); 
					//判断有没有当前扫描的用户有没有过期
					if (wechatUser.getExpireTime().getTime() - new Date().getTime() > 0){
						Cookie cookie = new Cookie("ticket", userService.addLoginTicket(wechatUser.getOpenId()));
						response.addCookie(cookie);
						cookie.setPath("/");
					}
				}
				else{
					//如果Introducer没过期 加RecommendTime天
					introduceFan.setRecommendNum(introduceFan.getRecommendNum()+1);
					if(introduceFan.getRecommendNum()<userSetting.getRecommendNum()){
						if(introduceFan.getExpireTime().getTime() - new Date().getTime() > 0){
							introduceFan.setExpireTime(new Date(introduceFan.getExpireTime().getTime()+3600*1000*24*userSetting.getRecommendTime()));
						}
						//如果过期了 在当前时间基础上加RecommendTime天
						else{
							introduceFan.setExpireTime(new Date(new Date().getTime()+3600*1000*24*userSetting.getRecommendTime()));
						}
					}
					else{
						java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");   
						java.util.Date date = null;
						try {
							date = format.parse("2099-12-25");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						introduceFan.setExpireTime(date);
					}
					fanService.updateFan(introduceFan);
					//注册当前用户
					Fan newFan =fanService.regFan(openId,owner.getUserId(),userSetting.getRegisterTime());
					hostHolder.setFan(newFan);
					hostHolder.setFanOwnerUser(userService.getUserByUserId(newFan.getBelongOwnerId()));
					Cookie cookie = new Cookie("ticket", userService.addLoginTicket(newFan.getOpenId()));
					cookie.setPath("/");
					response.addCookie(cookie);
					//给注册的用户发一份私信
					EventModel eventModel = new EventModel(EventType.MESSAGE);
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
					eventModel.setExt("content", "欢迎您的注册，您的到期时间为"+dateFormat.format(newFan.getExpireTime()));
					eventModel.setExt("fromId",newFan.getBelongOwnerId());
					eventModel.setExt("toId", newFan.getOpenId());
					eventProducer.fireEvent(eventModel);
			        //给推荐用户发送信息
					EventModel eventModel2 = new EventModel(EventType.MESSAGE);
					eventModel2.setExt("content", "您推荐用户成功，累计有效推荐"+introduceFan.getRecommendNum()+"人,您的到期时间更新为"+dateFormat.format(introduceFan.getExpireTime()));
					eventModel2.setExt("fromId",introduceFan.getBelongOwnerId());
					eventModel2.setExt("toId", introduceFan.getOpenId());
					eventProducer.fireEvent(eventModel2);
					return "theater/userOwnerInfo";
				}
				return "theater/userOwnerInfo";
			}
			else {
				return "theater/userOwnerInfo";
			}
		}
		return "redirect:/error";
	}
	@RequestMapping("/messageBox")
	public String getMessageBox(Model model){
//		model.addAttribute("wechatUser", hostHolder.getFan());
//		model.addAttribute("wechatOwnerUser", hostHolder.getFanOwnerUser());
//		List<Message> messageList = messageService.getMessageList(hostHolder.getFan().getOpenId(), 0, 100);
//		model.addAttribute("messageList", messageList);
//		messageService.updateReadStatus(hostHolder.getFan().getOpenId());
//		return "theater/messageBox";
		return "redirect:/userInfo";
	}
}

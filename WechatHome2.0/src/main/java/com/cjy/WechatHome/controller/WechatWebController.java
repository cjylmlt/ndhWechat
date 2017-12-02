package com.cjy.WechatHome.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.Message;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.model.WechatUser;
import com.cjy.WechatHome.service.MessageService;
import com.cjy.WechatHome.service.UserService;
import com.cjy.WechatHome.service.WechatUserService;
import com.cjy.WechatHome.util.WechatUtil;

@Controller
public class WechatWebController {
	private static final String HOST_URL = "http://mxd.burod.cn";
	//private static final String HOST_URL = "http://fortestwechat.free.ngrok.cc";
	@Autowired
	HostHolder hostHolder;
	@Autowired
	WechatUserService wechatUserService;
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	@Autowired
	EventProducer eventProducer;
	@RequestMapping("/expireWarning")
	public String expireWarning(Model model,@RequestParam("userId")String userId){
		WechatUser wechatUser = wechatUserService.selectWechatUser(userId);
		User wechatOwnerUser = userService.getUserByUserId(wechatUser.getBelongOwnerId());
		model.addAttribute("wechatUser", wechatUser);
		model.addAttribute("wechatOwnerUser", wechatOwnerUser);
		model.addAttribute("messageUnreadCount", messageService.getUnreadCount(wechatUser.getOpenId()));
		String targetUrl = HOST_URL+"/subscribe?introducerId="+wechatUser.getOpenId();
		String packUrl = WechatUtil.packWebUrl(targetUrl);
		try {
			packUrl = URLEncoder.encode(packUrl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String qrCode = "http://qr.topscan.com/api.php?text="+ packUrl;
		model.addAttribute("qrCode", qrCode);
		return "expireWarning";
	}
	@RequestMapping("/userInfo")
	public String getUserInfo(Model model){
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		model.addAttribute("wechatOwnerUser", hostHolder.getWechatOwnerUser());
		model.addAttribute("messageUnreadCount", messageService.getUnreadCount(hostHolder.getWechatUser().getOpenId()));
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
		return "userInfo";
	}
	
	@RequestMapping("/myQrCode")
	public String getQrcode(Model model){
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
		return "myQrCode";
	}
	@RequestMapping("/poster")
	public String getPoster(Model model){
		return "poster";
	}
	
	@RequestMapping("/subscribe")
	public String getSubInfo(Model model,@RequestParam("introducerId")String introducerId,HttpServletRequest request,HttpServletResponse response){
		//判断是否为有效Id
		if(wechatUserService.isWchatUserExist(introducerId)){
			//判断当前账号是否注册过
			String code = request.getParameter("code");
			String openId = WechatUtil.getUserOpenId(code);
			if(openId!=null&&!openId.equals("")){
				if(wechatUserService.isWchatUserExist(openId)){
					WechatUser wechatUser = wechatUserService.selectWechatUser(openId); 
					//判断有没有当前扫描的用户有没有过期
					if (wechatUser.getExpireTime().getTime() - new Date().getTime() > 0){
						Cookie cookie = new Cookie("ticket", userService.addLoginTicket(wechatUser.getOpenId()));
						response.addCookie(cookie);
						cookie.setPath("/");
					}
				}
				else{
					WechatUser introduceUser = wechatUserService.selectWechatUser(introducerId);
					//如果Introducer没过期 加15天
					introduceUser.setRecommendNum(introduceUser.getRecommendNum()+1);
					if(introduceUser.getRecommendNum()<5){
						if(introduceUser.getExpireTime().getTime() - new Date().getTime() > 0){
							introduceUser.setExpireTime(new Date(introduceUser.getExpireTime().getTime()+3600*1000*24*15));
						}
						//如果过期了 在当前时间基础上加15天
						else{
							introduceUser.setExpireTime(new Date(new Date().getTime()+3600*1000*24*15));
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
						introduceUser.setExpireTime(date);
					}
					wechatUserService.updateWechatUser(introduceUser);
					//找到owner
					User owner = userService.getUserByUserId(introduceUser.getBelongOwnerId());
					model.addAttribute("owner", owner);
					//注册当前用户
					WechatUser newWechatUser =wechatUserService.regWechatUser(openId,owner.getUserId());
					hostHolder.setWechatUser(newWechatUser);
					hostHolder.setWechatOwnerUser(userService.getUserByUserId(newWechatUser.getBelongOwnerId()));
					Cookie cookie = new Cookie("ticket", userService.addLoginTicket(newWechatUser.getOpenId()));
					cookie.setPath("/");
					response.addCookie(cookie);
					//给注册的用户发一份私信
					EventModel eventModel = new EventModel(EventType.MESSAGE);
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
					eventModel.setExt("content", "欢迎您的注册，您的到期时间为"+dateFormat.format(newWechatUser.getExpireTime()));
					eventModel.setExt("fromId",newWechatUser.getBelongOwnerId());
					eventModel.setExt("toId", newWechatUser.getOpenId());
					eventProducer.fireEvent(eventModel);
			        //给推荐用户发送信息
					EventModel eventModel2 = new EventModel(EventType.MESSAGE);
					eventModel2.setExt("content", "您推荐用户成功，您的到期时间更新为"+dateFormat.format(introduceUser.getExpireTime()));
					eventModel2.setExt("fromId",introduceUser.getBelongOwnerId());
					eventModel2.setExt("toId", introduceUser.getOpenId());
					eventProducer.fireEvent(eventModel2);
					return "userOwnerInfo";
				}
			}
			else {
				return "/error";
			}
		}
		return "redirect:/v";
	}
	@RequestMapping("/messageBox")
	public String getMessageBox(Model model){
		model.addAttribute("wechatUser", hostHolder.getWechatUser());
		model.addAttribute("wechatOwnerUser", hostHolder.getWechatOwnerUser());
		List<Message> messageList = messageService.getMessageList(hostHolder.getWechatUser().getOpenId(), 0, 100);
		model.addAttribute("messageList", messageList);
		messageService.updateReadStatus(hostHolder.getWechatUser().getOpenId());
		return "messageBox";
	}
}

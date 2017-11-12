package com.cjy.WechatHome.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
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
	private static final String HOST_URL = "http://fortestwechat.free.ngrok.cc";
	@Autowired
	HostHolder hostHolder;
	@Autowired
	WechatUserService wechatUserService;
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
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
					introduceUser.setExpireTime(new Date(introduceUser.getExpireTime().getTime()+3600*1000*24*7));
					wechatUserService.updateExpireTime(introduceUser);
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
					Message regMessage = new Message();
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
					regMessage.setContent("欢迎您的注册，您的到期时间为"+dateFormat.format(newWechatUser.getExpireTime()));
					regMessage.setFromId(newWechatUser.getBelongOwnerId());
					regMessage.setToId(newWechatUser.getOpenId());
					String conversationId;
			        if (newWechatUser.getBelongOwnerId().compareTo(newWechatUser.getOpenId())>=0) {
			        	conversationId = String.format("%s_%s", newWechatUser.getBelongOwnerId(), newWechatUser.getOpenId());
			        } else {
			        	conversationId = String.format("%s_%s", newWechatUser.getOpenId(), newWechatUser.getBelongOwnerId());
			        }
			        regMessage.setConversationId(conversationId);
			        regMessage.setCreatedDate(new Date());
			        regMessage.setHasRead(0);
			        messageService.addMessage(regMessage);
			        //给推荐用户发送信息
			        Message introduceMessage = new Message();
			        introduceMessage.setContent("您推荐用户成功，您的到期时间更新为"+dateFormat.format(introduceUser.getExpireTime()));
			        introduceMessage.setFromId(introduceUser.getBelongOwnerId());
			        introduceMessage.setToId(introduceUser.getOpenId());
			        if (introduceUser.getBelongOwnerId().compareTo(introduceUser.getOpenId())>=0) {
			        	conversationId = String.format("%s_%s", introduceUser.getBelongOwnerId(), introduceUser.getOpenId());
			        } else {
			        	conversationId = String.format("%s_%s", introduceUser.getOpenId(), introduceUser.getBelongOwnerId());
			        }
			        introduceMessage.setConversationId(conversationId);
			        introduceMessage.setCreatedDate(new Date());
			        introduceMessage.setHasRead(0);
			        messageService.addMessage(introduceMessage);
					return "userOwnerInfo";
				}
			}
			else {
				return "error/error";
			}
		}
		return "/v";
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

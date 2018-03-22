package com.cjy.WechatHome.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjy.WechatHome.web.model.UserSetting;
import com.cjy.WechatHome.web.service.UserSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.dao.LoginTicketDao;
import com.cjy.WechatHome.theater.model.Fan;
import com.cjy.WechatHome.theater.service.FanService;
import com.cjy.WechatHome.theater.service.MessageService;
import com.cjy.WechatHome.util.WechatUtil;
import com.cjy.WechatHome.web.model.HostHolder;
import com.cjy.WechatHome.web.model.LoginTicket;
import com.cjy.WechatHome.web.model.User;
import com.cjy.WechatHome.web.service.UserService;

@Component
public class PermissionRequiredInterceptor implements HandlerInterceptor {
	@Autowired
	HostHolder hostHolder;
	@Autowired
	FanService fanService;
	@Autowired
	LoginTicketDao loginTicketDao;
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	@Autowired
	EventProducer eventProducer;
	@Autowired
	UserSettingService userSettingService;
	@Value("${wechat.fakeuser}")
	String fakeuser;
	private static final Logger logger = LoggerFactory.getLogger(PermissionRequiredInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		hostHolder.clearFanOwnerUsers();
		hostHolder.clearFans();
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String ticket = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("ticket".equals(cookie.getName()))
					ticket = cookie.getValue();
			}
			if (ticket != null) {
				LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
				if (loginTicket != null && loginTicket.getStatus() != 1
						&& !loginTicket.getExpired().before(new Date())) {
					Fan fan = fanService.selectFan(loginTicket.getUserId());
					if (fan != null&&(fan.getExpireTime().getTime() - new Date().getTime() > 0)) {
						hostHolder.setFan(fan);
						hostHolder.setFanOwnerUser(userService.getUserByUserId(fan.getBelongOwnerId()));
						List<LoginTicket> loginTicketList = loginTicketDao.selectById(fan.getOpenId());
						if(loginTicketList.size()<2){
							userService.addLoginTicket(fan.getOpenId());
							userService.addLoginTicket(fan.getOpenId());
							response.sendRedirect("/poster?next="+request.getRequestURI()+getNextUrlParam(request));
						}
						return true;
					}
				}

			}
		}
		// 沒有cookie或者cookie过期

		//测试模式
		if (fakeuser.equals("true")){
			Fan fan = fanService.selectFan("oKtSQ0--0Ym5qGy4gGqZxWt4oLsw");
			hostHolder.setFan(fan);
			hostHolder.setFanOwnerUser(userService.getUserByUserId(fan.getBelongOwnerId()));
			List<LoginTicket> loginTicketList = loginTicketDao.selectById(fan.getOpenId());
			if(loginTicketList.size()<2){
				userService.addLoginTicket(fan.getOpenId());
				userService.addLoginTicket(fan.getOpenId());
				response.sendRedirect("/poster?next="+request.getRequestURI()+getNextUrlParam(request));
			}
			return true;
		}
		//非测试模式
		String code = request.getParameter("code");
		String openId = WechatUtil.getUserOpenId(code);
		if (openId.equals("")) {
			response.sendRedirect("/error");
			return false;
		}
		if (fanService.isFanExist(openId)) {
			Fan fan = fanService.selectFan(openId);
			// 用户没有过期
			if (fan.getExpireTime().getTime() - new Date().getTime() > 0) {
				hostHolder.setFan(fan);
				hostHolder.setFanOwnerUser(userService.getUserByUserId(fan.getBelongOwnerId()));
				Cookie cookie = new Cookie("ticket", userService.addLoginTicket(fan.getOpenId()));
				cookie.setPath("/");
				response.addCookie(cookie);
				List<LoginTicket> loginTicketList = loginTicketDao.selectById(fan.getOpenId());
				if(loginTicketList.size()<2){
					userService.addLoginTicket(fan.getOpenId());
					userService.addLoginTicket(fan.getOpenId());
					response.sendRedirect("/poster?next="+request.getRequestURI()+getNextUrlParam(request));
				}
				return true;
			}
			// 用户过期了
			else {
				hostHolder.setFan(fan);
				response.sendRedirect("/expireWarning?userId=" + fan.getOpenId());
				// response.sendRedirect("/userInfo");
				return false;
			}
		}
		// 新用户
		else {
			String ownerId = request.getParameter("state");
			if (ownerId == null || ownerId.equals("")) {
				// 无人推荐 则算在nhd上
				ownerId = "gh_936af05e57ce";
			}
			User owner = userService.getUserByUserId(ownerId);
			if (owner == null) {
				// 无人推荐 则算在nhd上
				ownerId = "gh_936af05e57ce";
			}
			UserSetting userSetting = userSettingService.selectUserSetting(owner.getId());
			Fan fan = fanService.regFan(openId, ownerId,userSetting.getRegisterTime());
			hostHolder.setFan(fan);
			hostHolder.setFanOwnerUser(userService.getUserByUserId(fan.getBelongOwnerId()));
			Cookie cookie = new Cookie("ticket", userService.addLoginTicket(fan.getOpenId()));
			cookie.setPath("/");
			response.addCookie(cookie);
			// 给注册的用户发一份私信
			EventModel eventModel = new EventModel(EventType.MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			eventModel.setExt("content", "欢迎您的注册，您的到期时间为" + dateFormat.format(fan.getExpireTime()));
			eventModel.setExt("fromId", fan.getBelongOwnerId());
			eventModel.setExt("toId", fan.getOpenId());
			eventProducer.fireEvent(eventModel);
			//跳转vip通知

			List<LoginTicket> loginTicketList = loginTicketDao.selectById(fan.getOpenId());
			if(loginTicketList.size()<2){
				userService.addLoginTicket(fan.getOpenId());
				userService.addLoginTicket(fan.getOpenId());
				response.sendRedirect("/poster?next="+request.getRequestURI()+getNextUrlParam(request));
			}
			return true;
		}

		//return true;
	}
	public String getNextUrlParam(HttpServletRequest request){
		StringBuilder address = new StringBuilder();
		address.append("?");
		Map<String, String[]> paramMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			if(entry.getKey().equals("make")||entry.getKey().equals("play")||entry.getKey().equals("mso"))
				address.append(entry.getKey()+"="+entry.getValue()[0]);
		}
		return address.toString();
	}
}

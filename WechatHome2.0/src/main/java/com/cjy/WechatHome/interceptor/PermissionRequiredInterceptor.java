package com.cjy.WechatHome.interceptor;


import java.lang.ProcessBuilder.Redirect;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.tools.config.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.WechatHome.dao.LoginTicketDao;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.LoginTicket;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.model.WechatUser;
import com.cjy.WechatHome.service.WechatUserService;
import com.cjy.WechatHome.util.WechatUtil;
@Component
public class PermissionRequiredInterceptor implements HandlerInterceptor{
@Autowired
HostHolder hostHolder;
@Autowired
WechatUserService wechatUserService;
@Autowired
LoginTicketDao loginTicketDao;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
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
		if(request.getCookies()!=null){
			for(Cookie cookie:request.getCookies()){
				if("ticket".equals(cookie.getName()))
					ticket = cookie.getValue();
			}
			if(ticket!=null){
				LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
				if(loginTicket==null||loginTicket.getStatus()==1||loginTicket.getExpired().before(new Date())){
					return true;				
				}
				WechatUser wechatUser = wechatUserService.selectWechatUser(loginTicket.getUserId());
				hostHolder.setWechatUser(wechatUser);
			}
		}
		else{
			String code = request.getParameter("code");
			String openId = WechatUtil.getUserOpenId(code);
			if(wechatUserService.isWchatUserExist(openId)){
				//判断是否到期
				WechatUser wechatUser = wechatUserService.selectWechatUser(openId);
				if(wechatUser.getExpireTime().getTime()-new Date().getTime()>0){
					return true;
				}
				else{
					response.sendRedirect("/expireWarning");
				}
			}
			else{
				WechatUser wechatUser = new WechatUser();
				wechatUser.setOpenId(openId);
				wechatUser.setUserName("adsfasdf");
				wechatUser.setExpireTime(new Date(new Date().getTime()+3600*1000*24*7));
				wechatUserService.regWechatUser(wechatUser);
			}
			return true;
		}
	}

}

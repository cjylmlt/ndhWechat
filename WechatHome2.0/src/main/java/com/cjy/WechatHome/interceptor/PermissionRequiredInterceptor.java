package com.cjy.WechatHome.interceptor;


import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.WechatHome.dao.LoginTicketDao;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.LoginTicket;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.model.WechatUser;
import com.cjy.WechatHome.service.UserService;
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
@Autowired
UserService userService;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		hostHolder.clearWechatOwnerUsers();
		hostHolder.clearWechatUser();
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
					response.sendRedirect("/expireWarning?next="+request.getRequestURI());				
				}
				WechatUser wechatUser = wechatUserService.selectWechatUser(loginTicket.getUserId());
				if(wechatUser!=null){
					hostHolder.setWechatUser(wechatUser);
					hostHolder.setWechatOwnerUser(userService.getUserByUserId(wechatUser.getBelongOwnerId()));
				}

			}
			else{
				String code = request.getParameter("code");
				String openId = WechatUtil.getUserOpenId(code);
				if(openId.equals(""))
					response.sendRedirect("/error");	
				if(wechatUserService.isWchatUserExist(openId)){
					WechatUser wechatUser = wechatUserService.selectWechatUser(openId);
					//没有过期
					if(wechatUser.getExpireTime().getTime()-new Date().getTime()>0){
						hostHolder.setWechatUser(wechatUser);
						hostHolder.setWechatOwnerUser(userService.getUserByUserId(wechatUser.getBelongOwnerId()));
						Cookie cookie = new Cookie("ticket", userService.addLoginTicket(wechatUser.getOpenId()));
						cookie.setPath("/");
						response.addCookie(cookie);
						return true;
					}
					//过期了
					else{
						hostHolder.setWechatUser(wechatUser);
						response.sendRedirect("/expireWarning?next="+request.getRequestURI());	
					}
				}
				//新用户
				else{
					WechatUser wechatUser = new WechatUser();
					wechatUser.setOpenId(openId);
					wechatUser.setBelongOwnerId("gh_936af05e57ce");
					wechatUser.setUserName(UUID.randomUUID().toString().substring(0, 10));
					wechatUser.setExpireTime(new Date(new Date().getTime()+3600*1000*24*7));
					wechatUserService.regWechatUser(wechatUser);
					hostHolder.setWechatUser(wechatUser);
					hostHolder.setWechatOwnerUser(userService.getUserByUserId(wechatUser.getBelongOwnerId()));
					Cookie cookie = new Cookie("ticket", userService.addLoginTicket(wechatUser.getOpenId()));
					cookie.setPath("/");
					response.addCookie(cookie);
					return true;
				}
			}
		}
		return true;
	}

}

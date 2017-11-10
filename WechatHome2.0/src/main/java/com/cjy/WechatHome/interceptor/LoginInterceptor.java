package com.cjy.WechatHome.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.WechatHome.dao.LoginTicketDao;
import com.cjy.WechatHome.dao.UserDao;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.LoginTicket;
import com.cjy.WechatHome.model.User;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	@Autowired
	LoginTicketDao loginTicketDao;
	@Autowired
	UserDao userDao;
	@Autowired
	HostHolder hostHolder;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		hostHolder.clear();
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
		if(modelAndView!=null)
			modelAndView.addObject("user", hostHolder.getUser());
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String ticket = null;
		if(arg0.getCookies()!=null){
			for(Cookie cookie:arg0.getCookies()){
				if("ticket".equals(cookie.getName()))
					ticket = cookie.getValue();
			}
			
		}
		if(ticket!=null){
			LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
			if(loginTicket==null||loginTicket.getStatus()==1||loginTicket.getExpired().before(new Date())){
				return true;				
			}
			User user = userDao.selectById(Integer.valueOf(loginTicket.getUserId()));
			hostHolder.setUser(user);
		}
		return true;
	}
	
}

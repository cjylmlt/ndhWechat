package com.cjy.WechatHome.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cjy.WechatHome.interceptor.LoginInterceptor;
import com.cjy.WechatHome.interceptor.LoginRequiredInterceptor;
@Component
public class WendaConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	LoginInterceptor loginInterceptor;
	@Autowired
	LoginRequiredInterceptor LoginRequiredInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(loginInterceptor);
		registry.addInterceptor(LoginRequiredInterceptor).addPathPatterns("/user/*");
		super.addInterceptors(registry);
	}
	
	
	
}

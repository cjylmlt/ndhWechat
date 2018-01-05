package com.cjy.WechatHome.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.cjy.WechatHome.interceptor.LoginInterceptor;
import com.cjy.WechatHome.interceptor.LoginRequiredInterceptor;
import com.cjy.WechatHome.interceptor.PermissionRequiredInterceptor;
@Component
public class WechatConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	LoginInterceptor loginInterceptor;
	@Autowired
	LoginRequiredInterceptor LoginRequiredInterceptor;
	@Autowired
	PermissionRequiredInterceptor permissionRequiredInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(loginInterceptor).addPathPatterns("/","/user/*","/userSetting","/definedReply/addTextReply","/definedReply/addPicReply","/definedReply/delete","/definedReply/update","/definedReply/updateAd","/wechatStatics","/search");
		registry.addInterceptor(LoginRequiredInterceptor).addPathPatterns("/user/*","/wechatStatics","/search");
	    registry.addInterceptor(permissionRequiredInterceptor).addPathPatterns("/v","/play/**","/movie/**","/userInfo","/messageBox","/myQrCode","/play.php","/mplay.php");
		super.addInterceptors(registry);
	}
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).
            favorParameter(false).
            ignoreAcceptHeader(false).
            useJaf(false).
            defaultContentType(MediaType.TEXT_HTML).
            mediaType("json", MediaType.APPLICATION_JSON);
    }
	
	
}

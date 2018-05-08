package com.cjy.WechatHome.theater.Aspect;

import com.cjy.WechatHome.data.HostHolder;
import com.cjy.wechathome.data.web.User;
import com.cjy.WechatHome.web.service.UserService;
import com.cjy.WechatHome.web.service.UserSettingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.support.BindingAwareModelMap;

@Aspect
@Component
public class AlipayAspect {
    @Autowired
    UserSettingService userSettingService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Pointcut("execution(* com.cjy.WechatHome.theater.controller.ProxyController.*(..))")
    public void alipayPointcut(){}
    @Around("alipayPointcut()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        User user;
        if(hostHolder.getFan()==null)
            user = null;
        else
            user = userService.getUserByUserId(hostHolder.getFan().getBelongOwnerId());
        //访问目标方法的参数：
        Object[] args = point.getArgs();
        for (Object arg:args) {
            if (arg.getClass() == BindingAwareModelMap.class) {
               BindingAwareModelMap map = (BindingAwareModelMap) arg;
               if(user!=null)
                   map.put("alipayString",userSettingService.selectUserSetting(user.getId()).getAlipayKey());
            }
        }
        //用改变后的参数执行目标方法
        Object returnValue = point.proceed(args);
        return returnValue;
    }
}

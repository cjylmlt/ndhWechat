package com.cjy.WechatHome.theater.Aspect;

import com.cjy.WechatHome.util.JedisAdapter;
import com.cjy.WechatHome.util.RedisKeyUtil;
import com.cjy.wechathome.data.web.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PageCacheAspect {
    @Autowired
    JedisAdapter jedisAdapter;
    @Pointcut("execution(* com.cjy.WechatHome.spider.VideoSpider.getMovieSource(..))")
    void pageCacheCutPoint(){};
    @Around("pageCacheCutPoint()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        String address = (String)args[0];
        User user = null;
        for(Object arg:args){
            if(arg!=null&&arg.getClass()==User.class){
                user = (User)arg;
            }
        }
        String content = jedisAdapter.get(RedisKeyUtil.getViedoPageKey()+address);
        if(content!=null) {
            content = addUserInfo(user,content);
            return content;
        }
        else{
            Object result = point.proceed();
            jedisAdapter.setex(RedisKeyUtil.getViedoPageKey()+address,3600*6,(String)result);
            result = addUserInfo(user,(String)result);
            return result;
        }
    }
    public String addUserInfo(User user, String urlResult){
        if(user!=null){
            urlResult = urlResult.replaceAll("马小呆", user.getNoteOne());
//			urlResult = urlResult.replaceAll("images/qrcode.png", "/uploadImages/"+user.getUserId()+"/qrcode");
            urlResult = urlResult.replaceAll("images/qrcode.png", "/uploadImages/"+user.getUserId()+"/qrcode");
            urlResult = urlResult.replaceAll("images/zfb.png", "/uploadImages/"+user.getUserId()+"/zfb");
            urlResult = urlResult.replaceAll("images/wx.png", "/uploadImages/"+user.getUserId()+"/wx");
            urlResult = urlResult.replaceAll("images/sologo.png", "/uploadImages/"+user.getUserId()+"/qrcode");

        }
        else{
            urlResult = urlResult.replaceAll("马小呆", "");
            urlResult = urlResult.replaceAll("images/qrcode.png", "");
            urlResult = urlResult.replaceAll("images/zfb.png", "");
            urlResult = urlResult.replaceAll("images/wx.png", "");
            urlResult = urlResult.replaceAll("images/sologo.png", "");
        }
        urlResult=urlResult.replaceAll("images/","http://vv.woaik.com/images/");
        urlResult=urlResult.replaceAll("http://vv.woaik.com/images/1280jiazai.png","http://47.96.147.91/images/jiazai.png");
        return urlResult;
    }
}

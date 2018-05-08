package com.cjy.WechatHome.web.controller;


import com.cjy.WechatHome.data.HostHolder;
import com.cjy.wechathome.data.web.User;
import com.cjy.wechathome.data.web.UserSetting;
import com.cjy.WechatHome.web.service.UserService;
import com.cjy.WechatHome.web.service.UserSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserVipSettingController {
	private static final Logger logger = LoggerFactory.getLogger(UserVipSettingController.class);
	@Autowired
    UserService userService;
	@Autowired
    HostHolder hostHolder;
	@Autowired
    UserSettingService userSettingService;
    @RequestMapping(path={"/user/{userId}/vipSetting"})
    public String getVipSetting(Model model,@PathVariable("userId") int userId){
        User visitUser = hostHolder.getUser();
        User owner = userService.getUser(userId);
        if(visitUser.getUsername().equals("admin")||visitUser.getUsername().equals(owner.getUsername())){
            model.addAttribute("userVipSetting",userSettingService.selectUserSetting(userId));
            model.addAttribute("settingUser",userService.getUser(userId));
        }
        return "user_vip_setting";
    }
    @RequestMapping(path={"user/{userId}/vipSetting/update"},method = {RequestMethod.POST,RequestMethod.GET})
    public String updateVipSetting(Model model,@PathVariable("userId")int userId,
                                   @RequestParam("registerTime") int registerTime,
                                   @RequestParam("recommendTime") int recommendTime,
                                   @RequestParam("recommendNum") int recommendNum,
                                   @RequestParam("alipayKey") String alipayKey
                                  ){
        User visitUser = hostHolder.getUser();
        User owner = userService.getUser(userId);
        if(visitUser.getUsername().equals("admin")||visitUser.getUsername().equals(owner.getUsername())){
            UserSetting userSetting = userSettingService.selectUserSetting(userId);
            userSetting.setRegisterTime(registerTime);
            userSetting.setRecommendTime(recommendTime);
            userSetting.setRecommendNum(recommendNum);
            userSetting.setAlipayKey(alipayKey);
            userSettingService.updateUserSetting(userSetting);
        }
        return "redirect:/user/"+userId+"/vipSetting";
    }
}

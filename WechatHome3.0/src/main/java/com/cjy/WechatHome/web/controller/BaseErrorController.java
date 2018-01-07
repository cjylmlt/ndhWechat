package com.cjy.WechatHome.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "error")
public class BaseErrorController implements ErrorController {
private static final Logger logger = LoggerFactory.getLogger(BaseErrorController.class);

    @Override
    public String getErrorPath() {
        //logger.info("出错啦！进入自定义错误控制器");
        return "error/error";
    }

    @RequestMapping
    public String error(Model model) {
    	String myUrl = "http://www.baidu.com";
    	model.addAttribute("myUrl", myUrl);
        return getErrorPath();
    }

}
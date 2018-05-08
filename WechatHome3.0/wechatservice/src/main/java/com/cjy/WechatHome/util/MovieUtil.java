package com.cjy.WechatHome.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MovieUtil {
    public static String getMovieParam(HttpServletRequest request){
        StringBuilder address = new StringBuilder();
        address.append(request.getRequestURI()).append("?");
        Map<String, String[]> paramMap = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if(entry.getKey().equals("make")||entry.getKey().equals("play")||entry.getKey().equals("mso"))
                address.append(entry.getKey()+"="+entry.getValue()[0]);
        }
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if(entry.getKey().equals("code"))
                address.append("&"+entry.getKey()+"="+entry.getValue()[0]);
        }
        return address.toString();
    }
}

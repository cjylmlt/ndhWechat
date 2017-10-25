package com.imooc.test;

import com.imooc.po.AccessToken;
import com.imooc.type.MediaType;
import com.imooc.util.OnlineMediaUtil;
import com.imooc.util.WechatUtil;


import net.sf.json.JSONObject;

public class WechatTest {
	private static final String GET_ONLINE_MEDIA_URL= "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	public static void main(String[] args){
		sendMediaMessage();
	}
	public static void sendMenuToWechat(){
		String token = WechatUtil.getAccessToken().getToken();
		String menu = JSONObject.fromObject(WechatUtil.packMenu()).toString();
		int result =  WechatUtil.createMenu(token, menu);
		System.out.println(result);
	}
	public static void sendPicToWechat(){
		
	}
	public static void sendMediaMessage(){
		String token = WechatUtil.getAccessToken().getToken();
		String url = GET_ONLINE_MEDIA_URL.replace("ACCESS_TOKEN", token);
		String message = JSONObject.fromObject(OnlineMediaUtil.getOnlineMediaMessage(MediaType.image)).toString();
		WechatUtil.getOnlineMedia(url,message);
	}
}

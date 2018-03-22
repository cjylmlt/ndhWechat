package com.cjy.WechatHome.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.cjy.WechatHome.wechat.model.AccessToken;
import com.cjy.WechatHome.wechat.model.Button;
import com.cjy.WechatHome.wechat.model.ClickButton;
import com.cjy.WechatHome.wechat.model.Menu;
import com.cjy.WechatHome.wechat.model.ViewButton;
import com.cjy.WechatHome.wechat.model.WechatUser;

import net.sf.json.JSONObject;
@Configuration
public class WechatUtil {
	private static  String APPID ;
	private static  String APPSECRET;
	//private static final String APPID = "wx39a6afe0d6c218de";
	//private static final String APPSECRET="1087e9433396c0930c3b8d0b6960c948";
	//private static final String APPID = "wxcc7030da657c484d";
	//private static final String APPSECRET="4496ca6d9e4ffce5cdb8f6d0b5d69057";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String WECHAT_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	private static final String CREATE_MENU_URL = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String WEB_WECHAT_USER_URL = " https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static final String GET_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	
	private static final String WEB_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	private static final String WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code ";
	private static  String HOST_URL ;

	public WechatUtil(@Value("${host.url}") String host,
			@Value("${wechat.appid}") String appid,
			@Value("${wechat.appsecret}") String appsecret){
		WechatUtil.HOST_URL = host;
		WechatUtil.APPID = appid;
		WechatUtil.APPSECRET = appsecret;
	}
	public static JSONObject doGetStr(String url){
		HttpClient httpClient = new HttpClient();
		GetMethod httpGet = new GetMethod(url);
		httpGet.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        //设置期望服务端返回的编码
		httpGet.setRequestHeader("Accept", "text/plain;charset=utf-8");
		String charSet = httpGet.getResponseCharSet();
		JSONObject jsonObject = null;
		try {
			int status = httpClient.executeMethod(httpGet);
			String content = httpGet.getResponseBodyAsString();
			content = new String(content.getBytes("ISO-8859-1"), "UTF-8");
			if(content!=null){
				jsonObject = JSONObject.fromObject(content);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static JSONObject doPostStr(String url,String outStr){
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		JSONObject jsonObject = null;
		httpPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
		httpPost.setRequestHeader("charset","UTF-8");
		
		httpPost.setRequestBody(outStr);
		
		try {
			int method = httpClient.executeMethod(httpPost);
			String content = httpPost.getResponseBodyAsString();
			if(content!=null){
				jsonObject = JSONObject.fromObject(content);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return jsonObject;
	}
	
	public static AccessToken getAccessToken(){
		AccessToken accessToken = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			accessToken.setToken(jsonObject.getString("access_token"));
			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return accessToken;
	}
	public static WechatUser getWechatUser(AccessToken accessToken,String openId){
		String url = WECHAT_USER_URL.replace("ACCESS_TOKEN", accessToken.getToken()).replace("OPENID", openId);
		JSONObject jsonObject = doGetStr(url);
		WechatUser user = new WechatUser();
		if(jsonObject!=null){
			user.setCity(jsonObject.getString("city"));
			user.setCountry(jsonObject.getString("country"));
			user.setHeadimgurl(jsonObject.getString("headimgurl"));
			user.setLanguage(jsonObject.getString("language"));
			user.setNickName(jsonObject.getString("nickname"));
			user.setOpenId(jsonObject.getString("p"));
			user.setProvice(jsonObject.getString("province"));
			user.setLanguage(jsonObject.getString("language"));
			user.setSex(jsonObject.getInt("sex"));
			user.setSubscribe(jsonObject.getInt("subscribe"));
			user.setSubscribeTime(jsonObject.getLong("subscribe_time"));
			return user;
		}
		return null;
	}

//    public static WechatUser getWebWechatUser(String accessToken,String openId){
//        String url = WECHAT_USER_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
//        JSONObject jsonObject = doGetStr(url);
//        WechatUser user = new WechatUser();
//        if(jsonObject!=null){
//            user.setCity(jsonObject.getString("city"));
//            user.setCountry(jsonObject.getString("country"));
//            user.setHeadimgurl(jsonObject.getString("headimgurl"));
//            user.setLanguage(jsonObject.getString("language"));
//            user.setNickName(jsonObject.getString("nickname"));
//            user.setOpenId(jsonObject.getString("openid"));
//            user.setProvice(jsonObject.getString("province"));
//            user.setSex(jsonObject.getInt("sex"));
//            return user;
//        }
//        return null;
//    }

	//组装菜单
	public static Menu packMenu(){
		Menu menu = new Menu();
		ClickButton button11= new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21 = new ViewButton();
		button21.setName("首页");
		button21.setType("view");
		button21.setUrl("http://www.baidu.com");
		
		ClickButton button31 = new ClickButton();
		button31.setName("扫码时间");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button31,button32});
		
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
		
	}
	
	public static int createMenu(String token,String menu){
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject!=null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	public static void sendMenuToWechat(){
		String token = WechatUtil.getAccessToken().getToken();
		String menu = JSONObject.fromObject(WechatUtil.packMenu()).toString();
		int result =  WechatUtil.createMenu(token, menu);
		System.out.println(result);
	}
	
	public static int getOnlineMedia(String url,String message){
		int result = 0;
		JSONObject jsonObject = doPostStr(url, message);
		if(jsonObject!=null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	public static String packWebUrl(String redirectUrl,String userId){
		String url = WEB_URL.replace("REDIRECT_URI", redirectUrl).replace("APPID", APPID).replace("SCOPE", "snsapi_base").replace("STATE", userId);
        //String url = WEB_URL.replace("REDIRECT_URI", redirectUrl).replace("APPID", APPID).replace("SCOPE", "snsapi_userinfo").replace("STATE", userId);
		return url;
	}
	public static String getUserOpenId(String code){
		if(code!=null){
			String url = WEB_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
			JSONObject jsonObject = doGetStr(url);
			String openId = "";
			if(jsonObject!=null&&jsonObject.containsKey("openid")){
				openId = jsonObject.getString("openid");
			}
			return openId;
		}
		else{
			return "";
		}
		
	}

//    public static String getWebAccessToken(String code){
//        if(code!=null){
//            String url = WEB_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
//            JSONObject jsonObject = doGetStr(url);
//            String access_token = "";
//            if(jsonObject!=null&&jsonObject.containsKey("access_token")){
//                access_token = jsonObject.getString("access_token");
//            }
//            return access_token;
//        }
//        else{
//            return "";
//        }
//
//    }

	public static String packUserUrl(String url,String userId){
		String resultUrl = url.replaceAll("http://v.burod.cn/v", HOST_URL);
		return packWebUrl(resultUrl,userId);
	}

//	public static Media getMedia(String token,String mediaId){
//		int result;
//		String url = GET_MEDIA_URL.replace("ACCESS_TOKEN", token);
//		JSONObject jsonObject = doPostStr(url, mediaId);
//		if(jsonObject!=null){
//			result = jsonObject.getInt("errcode");
//		}
//		if(result==0){//成功
//			
//			
//		}
//	}
}

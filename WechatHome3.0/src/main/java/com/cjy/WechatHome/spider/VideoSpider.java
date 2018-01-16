package com.cjy.WechatHome.spider;




import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cjy.WechatHome.dao.AdminSettingDao;
import com.cjy.WechatHome.dao.SpiderWebDao;
import com.cjy.WechatHome.theater.model.SpiderWeb;
import com.cjy.WechatHome.web.model.HostHolder;
import com.cjy.WechatHome.web.model.User;
import com.cjy.WechatHome.wechat.model.News;



@Component
public class VideoSpider {
	@Autowired
	SpiderWebDao spiderWebDao;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	AdminSettingDao adminSettingDao;
	public String sendGet(String url){//获得给定url的源文件
		String content="";
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(url);

		get.addRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
		get.addRequestHeader("Referer", "http://www.byjsj.cn/js/player/youku.html");
		get.addRequestHeader("Host","www.byjsj.cn");
		get.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
		try {
			int status = client.executeMethod(get);
			get.getParams().setContentCharset("UTF8");
//			InputStream inputStream = get.getResponseBodyAsStream();  
//			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));  
//			StringBuffer stringBuffer = new StringBuffer();  
//			String str= "";  
//			while((str = br.readLine()) != null){  
//				stringBuffer .append(str);  
//			}  
//			content = new String(stringBuffer.toString().getBytes("ISO-8859-1"),"UTF8");
//			content = content.replaceAll("http://cdn.tvmob.cn/b.js", "");
//			content = content.replaceAll("http://cdn.tvmob.cn/piaofu.js", "");
//			content = content.replaceAll("http://cdn.tvmob.cn/tongji.js", "");
//			content = content.replaceAll(";\\(function\\(\\)[\\s\\S]+?</script>", "");
			content = get.getResponseBodyAsString();
			//content = content.replaceAll("爱客影院", "马小呆影院");
			//System.out.println("getUrl:"+status);
			//System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			get.releaseConnection();
		}
		return content;
	}
	public List<News> getVideoMessage(String content){//给定level-one page的url和id中获得level-two page url
		List<News> newsList = new ArrayList<News>();
		SpiderWeb spiderWeb = spiderWebDao.selectUserWeb(Integer.parseInt(adminSettingDao.selectAdminSetting("hostWeb")));
		String url = spiderWeb.getSearchUrl()+content;
		String urlResult = sendGet(url);//获得level-one page源代码
		ArrayList<String> videoTitleList = regex(urlResult,spiderWeb.getTitleRegex1(),"");
		ArrayList<String> videoPicUrlList = regex(urlResult,spiderWeb.getPicRegex1(),"");
		ArrayList<String> videoUrlList = regex(urlResult,spiderWeb.getVideoRegex1(),spiderWeb.getWebUrl());
		if(spiderWeb.getPicRegex2()!=null){
			ArrayList<String> videoTitleList2 = regex(urlResult,spiderWeb.getTitleRegex2(),"");
			ArrayList<String> videoPicUrlList2 = regex(urlResult,spiderWeb.getPicRegex2(),"");
			ArrayList<String> videoUrlList2 = regex(urlResult,spiderWeb.getVideoRegex2(),spiderWeb.getWebUrl());
			videoTitleList.addAll(videoTitleList2);
			videoPicUrlList.addAll(videoPicUrlList2);
			videoUrlList.addAll(videoUrlList2);
		}
		int num = 0;
		if(videoTitleList.size()<=7)
			num = videoTitleList.size();
		else
			num = 7;
		for(int i=0; i<num;i++){
			News news = new News();
			news.setTitle(videoTitleList.get(i));
			news.setPicUrl(videoPicUrlList.get(i));
			news.setUrl(videoUrlList.get(i));
			news.setDescription("");
			newsList.add(news);
		}
		return newsList;
	}
	
	public List<News> newGetVideoMessage(String content){//给定level-one page的url和id中获得level-two page url
		List<News> newsList = new ArrayList<News>();
		SpiderWeb spiderWeb = spiderWebDao.selectUserWeb(7);//选择爱客
		String url = spiderWeb.getSearchUrl()+content;
		String urlResult = sendGet(url);//获得level-one page源代码
		ArrayList<String> videoTitleList = regex(urlResult,spiderWeb.getTitleRegex1(),"");
		ArrayList<String> videoPicUrlList = regex(urlResult,spiderWeb.getPicRegex1(),"");
		ArrayList<String> videoUrlList = regex(urlResult,spiderWeb.getVideoRegex1(),spiderWeb.getWebUrl());
		if(spiderWeb.getPicRegex2()!=null){
			ArrayList<String> videoTitleList2 = regex(urlResult,spiderWeb.getTitleRegex2(),"");
			ArrayList<String> videoPicUrlList2 = regex(urlResult,spiderWeb.getPicRegex2(),"");
			ArrayList<String> videoUrlList2 = regex(urlResult,spiderWeb.getVideoRegex2(),spiderWeb.getWebUrl());
			videoTitleList.addAll(videoTitleList2);
			videoPicUrlList.addAll(videoPicUrlList2);
			videoUrlList.addAll(videoUrlList2);
		}
		int num = 0;
		if(videoTitleList.size()<=7)
			num = videoTitleList.size();
		else
			num = 7;
		for(int i=0; i<num;i++){
			News news = new News();
			news.setTitle(videoTitleList.get(i));
			news.setPicUrl(videoPicUrlList.get(i));
			news.setUrl(videoUrlList.get(i));
			news.setDescription("");
			newsList.add(news);
		}
		return newsList;
	}
	
	
	public ArrayList<String> regex(String source,String target,String url){//从source文件中获取target格式的东西，然后和固定url拼起来
		ArrayList<String> result = new ArrayList<>();
		Pattern pattern = Pattern.compile(target);
		Matcher matcher = pattern.matcher(source);
		while(matcher.find()){
			result.add(url+matcher.group(1));
		}
		return result;
	}
	public String getIndexSource(){
		String url = "http://v.burod.cn/v/";
		String urlResult = sendGet(url);//获得level-one page源代码
		return urlResult;
	}
	public String getSearchSource(String content){
		String url = "http://v.burod.cn/v/seacher.php?sousuo="+content;
		String urlResult = sendGet(url);//获得level-one page源代码
		return urlResult;
	}
	public String getMovieSource(String url,User user){

		url = "http://v.burod.cn/v/"+url;
		String urlResult = sendGet(url);//获得level-one page源代码
		urlResult = urlResult.replaceFirst("<header[\\s\\S]+?</header>", "");
		urlResult = urlResult.replaceFirst("<a href=\"/\" target=\"_self\">[\\s\\S]+?<a href=\"/topic/index.php\" target=\"_self\">片单</a>", "");
		urlResult = urlResult.replaceFirst("<a href=\"/\" target=\"_self\">[\\s\\S]+?福利</span></a>", "");
		urlResult = urlResult.replaceFirst("<a href=\"http://mp.weixin.qq.com/s.+?</a>", "");
		
		urlResult = urlResult.replaceAll("<a target=\"blank\" class=\"gobtn\" href=\"http://xiaogui1.5awo.com\">爱客资源网</a>", "");
		urlResult = urlResult.replaceAll("<a target=\"blank\" class=\"gobtn\" href=\"https://jq.qq.com.+?\">加入Q群</a>", "");
		urlResult = urlResult.replaceAll("<div class=\"widget widget-textasst\">.+?</div>", "");
		//无效过滤
		urlResult = urlResult.replaceAll("<div class=\"asst asst-post_header\">.+?</div>", "");
		urlResult = urlResult.replaceAll("<div class=\"widget widget-textasst\">[\\s\\S]+?</div>", "");
		urlResult = urlResult.replaceAll("<div class=\"foornav\">[\\s\\S]+?</div>", "");
		urlResult = urlResult.replaceAll("<div id=\"SOHUCS\">[\\s\\S]+?\"重新注册\"</div>", "");
		urlResult = urlResult.replaceAll("<a href.+?站长统计</a>", "");
		urlResult = urlResult.replaceAll("<font color=\"red\">【影视】</font>", "");
		//过滤搜索栏
		urlResult = urlResult.replaceAll("<form method=\"get\" id=\"soform\"[\\s\\S]+?</form>", "");
		
		
		if(user!=null){
			urlResult = urlResult.replaceAll("爱客", user.getNoteOne());
//			urlResult = urlResult.replaceAll("images/qrcode.png", "/uploadImages/"+user.getUserId()+"/qrcode");
			urlResult = urlResult.replaceAll("images/qrcode.png", "/uploadImages/"+user.getUserId()+"/qrcode");
			urlResult = urlResult.replaceAll("images/zfb.png", "/uploadImages/"+user.getUserId()+"/zfb");
			urlResult = urlResult.replaceAll("images/wx.png", "/uploadImages/"+user.getUserId()+"/wx");
			urlResult = urlResult.replaceAll("images/sologo.png", "/uploadImages/"+user.getUserId()+"/qrcode");
			
		}
		else{
			urlResult = urlResult.replaceAll("爱客", "");
			urlResult = urlResult.replaceAll("images/qrcode.png", "");
			urlResult = urlResult.replaceAll("images/zfb.png", "");
			urlResult = urlResult.replaceAll("images/wx.png", "");
			urlResult = urlResult.replaceAll("images/sologo.png", "");
		}
		//把"/css/*","/js/*","/images/*"转到爱客去

		urlResult=urlResult.replaceAll("css/","http://v.woaik.com/css/");
		urlResult=urlResult.replaceAll("js/","http://v.woaik.com/js/");
		urlResult=urlResult.replaceAll("images/","http://v.woaik.com/images/");
		urlResult=urlResult.replaceAll("jiazai.png","");

		return urlResult;
	}
}

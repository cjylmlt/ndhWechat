package com.imooc.spider;




import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.imooc.po.News;




public class VideoSpider {
	private static String GOUDAITV_URL = "http://www.byjsj.cn";
	public static String sendGet(String url){//获得给定url的源文件
		String content="";
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(url);

		//get.addRequestHeader("","utf-8");
		try {
			int status = client.executeMethod(get);
			content = get.getResponseBodyAsString();
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
	public static List<News> getVideoMessage(String content){//给定level-one page的url和id中获得level-two page url
		List<News> newsList = new ArrayList<News>();
		String url = "http://www.byjsj.cn/search.php?searchword="+content;
		String urlResult = sendGet(url);//获得level-one page源代码
		ArrayList<String> videoTitleList = regex(urlResult,"<span class=\"sTit\">(.+?)</span>","");
		ArrayList<String> videoPicUrlList = regex(urlResult,"<img data-src=\"(.+?)\".+?src=.+?>","");
		ArrayList<String> videoUrlList = regex(urlResult,"<div class=\"con\">[\\s\\S]+?<a href=\"(.+?)\".+?target=\"_self\">[\\s\\S]+?</div>",GOUDAITV_URL);
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
	public static ArrayList<String> regex(String source,String target,String url){//从source文件中获取target格式的东西，然后和固定url拼起来
		ArrayList<String> result = new ArrayList<>();
		Pattern pattern = Pattern.compile(target);
		Matcher matcher = pattern.matcher(source);
		while(matcher.find()){
			result.add(url+matcher.group(1));
		}
		return result;
	}

	
//	public static void main(String[] args){
//		getVideoMessage();
//	}
}

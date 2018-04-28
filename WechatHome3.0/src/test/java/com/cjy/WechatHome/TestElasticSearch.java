package com.cjy.WechatHome;


import com.alibaba.fastjson.JSON;
import com.cjy.WechatHome.util.ElasticSearchUtil;
import com.cjy.WechatHome.wechat.model.MovieQueryMessage;
import com.cjy.WechatHome.wechat.model.NewsPo;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class TestElasticSearch {
	@Test
	public void add() throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        MovieQueryMessage movieQueryMessage = new MovieQueryMessage();
        movieQueryMessage.setQueryContent("adfasdfadfaasdfasdfa");
        movieQueryMessage.setOwnerId("123");
        movieQueryMessage.setQueryUserId("1234");
        movieQueryMessage.setQueryTime(simpleDateFormat.format(new Date()));
        List<NewsPo> news = new LinkedList<>();
        NewsPo newsPo = new NewsPo();
        newsPo.setKey("ddd");
        news.add(newsPo);
        movieQueryMessage.setQueryResult(JSON.toJSONString(news));
        ElasticSearchUtil.add("wechat","movie",movieQueryMessage);
        System.out.println();
	}
    @Test
    public void search() throws IOException {
       List<MovieQueryMessage> results = ElasticSearchUtil.fuzzySearch("wechat","movie","queryContent","帅",false);
       System.out.println();
    }
}

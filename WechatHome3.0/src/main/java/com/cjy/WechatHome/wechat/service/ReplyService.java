package com.cjy.WechatHome.wechat.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import com.alibaba.fastjson.JSON;
import com.cjy.WechatHome.util.ElasticSearchUtil;
import com.cjy.WechatHome.wechat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.spider.VideoSpider;
import com.cjy.WechatHome.util.MessageUtil;
import com.cjy.WechatHome.util.WechatUtil;
import com.cjy.WechatHome.web.model.Record;
import com.cjy.WechatHome.web.model.User;
import com.cjy.WechatHome.web.service.DefinedReplyService;
import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventType;

@Service
public class ReplyService {
    @Autowired
    DefinedReplyService definedReplyService;
    @Autowired
    VideoSpider videoSpider;
    @Autowired
    NewsService newsService;
    @Autowired
    EventProducer eventProducer;
    private static final String wechatIndex = "wechat";
    private static final String movieType = "movie";
    private static final String userIndex = "user";
    private static final String recordType = "movie";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public String textMessageReply(Map<String, String> map, User user, Record record) throws Exception {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");
        String message = null;
        message = getDefinedReply(content, toUserName, fromUserName, user);
        //先判断是否有固定回复
        if (message != null)
            return message;
            //没有的话进入影视搜索
        else {
            String urlContent = URLEncoder.encode(content, "UTF-8");
            return searchMovie(user, content, urlContent, toUserName, fromUserName, record);
        }
    }

    public String voiceMessageReply(Map<String, String> map, User user, Record record) throws Exception {
        String voice = map.get("Recognition");
        voice = voice.substring(0, voice.length() - 1);
        map.put("Content", voice);
        return textMessageReply(map, user, record);
    }

    public String EventMessageReply(Map<String, String> map, User user, Record record) throws Exception {
        String eventType = map.get("Event");
        String content = eventType;
        record.setContent(content);
        String msgType = map.get("MsgType");
        DefinedReply definedReply;
        String message = null;
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String reply = "";
        if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
            if ((definedReply = definedReplyService.getReply("新关注的回复", user.getUsername())) != null) {
                if (definedReply.getValue() != null) {
                    if (definedReply.getPicUrl() != null) {
                        List<News> newsList = new ArrayList<>();
                        News news = new News();
                        news.setDescription("");
                        news.setTitle(definedReply.getValue());
                        news.setPicUrl(definedReply.getPicUrl());
                        news.setUrl(definedReply.getUrl());
                        newsList.add(news);
                        message = MessageUtil.packNewsMessage(toUserName, fromUserName, newsList);
                    } else
                        message = MessageUtil.packText(toUserName, fromUserName, definedReply.getValue());
                } else {
                    message = MessageUtil.packText(toUserName, fromUserName, reply);
                }
            } else {
                //reply = "欢迎您的关注，回复影视关键词即可在线观看超清资源，如果喜欢的话，请推荐给你的朋友们哦";
                message = MessageUtil.packText(toUserName, fromUserName, reply);
            }
        } else if (MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)) {

        } else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
            String buttonKey = map.get("EventKey");
            if ("33".equals(buttonKey)) {
                reply = "商务合作请联系我\n微信：18822802281\nQQ：8652837\nTel：18822802281\n非客户不要轻易投食，合作请注明公司，合作项目，其他勿扰";
            } else {
                reply = "你点击了一下，真调皮";
            }
            message = MessageUtil.packText(toUserName, fromUserName, reply);
        } else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
            message = MessageUtil.packText(toUserName, fromUserName, reply);
        }
        return message;
    }

    public String getDefinedReply(String content, String toUserName, String fromUserName, User user) {
        DefinedReply definedReply = null;
        String message = null;
        if ((definedReply = definedReplyService.getReply(content, user.getUsername())) != null) {
            if (definedReply.getPicUrl() != null) {
                List<News> newsList = new ArrayList<>();
                News news = new News();
                news.setDescription("");
                news.setTitle(definedReply.getValue());
                news.setPicUrl(definedReply.getPicUrl());
                news.setUrl(definedReply.getUrl());
                newsList.add(news);
                message = MessageUtil.packNewsMessage(toUserName, fromUserName, newsList);
            } else
                message = MessageUtil.packText(toUserName, fromUserName, definedReply.getValue());
        }
        return message;
    }

    public String searchMovie(User user, String content, String urlContent, String toUserName, String fromUserName, Record record) throws UnsupportedEncodingException {
        long spiderTimeStart = System.currentTimeMillis();
        String reply, message;
        List<News> newsList = new ArrayList<>();
        List<NewsPo> newsPoList;
        if (user.getStatus() == 3)
            newsPoList = newsService.newSelectNewsPoByKey(content);
        else
            newsPoList = newsService.selectNewsPoByKey(content);
        if (newsPoList.size() > 0) {
            if (user.getStatus() == 3)
                newsList = newsService.newSelectNewsByKey(content);
            else
                newsList = newsService.selectNewsByKey(content);
            if ((new Date().getTime() - newsPoList.get(0).getUpdateTime().getTime()) > 3600 * 1000 * 24) {
                EventModel eventModel = new EventModel(EventType.VIDEOSPIDER);
                eventModel.setExt("urlContent", urlContent);
                eventModel.setExt("content", content);
                if (user.getStatus() == 3)
                    eventModel.setExt("type", "new");
                else
                    eventModel.setExt("type", "old");
                eventProducer.fireEvent(eventModel);
            }
            if (newsPoList.get(0).getTitle().equals("empty")) {
                return emptyResultMessage(newsList, content, user, toUserName, fromUserName);
            }
        } else {
            if (user.getStatus() == 3) {
                Future<List<News>> futureTask = cachedThreadPool.submit(new Callable<List<News>>() {
                    @Override
                    public List<News> call() throws Exception {
                        return videoSpider.newGetVideoMessage(urlContent);
                    }
                });
                try {
                    newsList = futureTask.get(4, TimeUnit.SECONDS);
                } catch (Exception e) {
                    message = MessageUtil.packText(toUserName, fromUserName, "当前观影高峰期，有" + (new Random().nextInt(1000) + 300) + "人正在搜索，观影通道拥堵，请再次输入影片名重试");
                    EventModel eventModel = new EventModel(EventType.VIDEOSPIDER);
                    eventModel.setExt("urlContent", urlContent);
                    eventModel.setExt("content", content);
                    if (user.getStatus() == 3)
                        eventModel.setExt("type", "new");
                    else
                        eventModel.setExt("type", "old");
                    eventProducer.fireEvent(eventModel);
                    return message;
                }
                newsService.newDeleteNewsByKey(content);
            } else {
                newsList = videoSpider.getVideoMessage(urlContent);
                newsService.deleteNewsByKey(content);
            }
            if (newsList.size() == 0) {
                NewsPo newsPo = new NewsPo();
                newsPo.setDescription("");
                newsPo.setKey(content);
                newsPo.setPicUrl("");
                newsPo.setTitle("empty");
                newsPo.setUpdateTime(new Date());
                newsPo.setUrl("");
                if (user.getStatus() == 3)
                    newsService.newInsertNews(newsPo);
                else
                    newsService.insertNews(newsPo);
            } else {
                List<NewsPo> newsPos = new LinkedList<>();
                for (News n : newsList) {
                    NewsPo newsPo = new NewsPo();
                    newsPo.setDescription(n.getDescription());
                    newsPo.setKey(content);
                    newsPo.setPicUrl(n.getPicUrl());
                    newsPo.setTitle(n.getTitle());
                    newsPo.setUpdateTime(new Date());
                    newsPo.setUrl(n.getUrl());
                    newsPos.add(newsPo);
                    if (user.getStatus() == 3)
                        newsService.newInsertNews(newsPo);
                    else
                        newsService.insertNews(newsPo);
                }
                MovieQueryMessage movieQueryMessage = new MovieQueryMessage(fromUserName, user.getUserId(), content, JSON.toJSONString(newsPos), new Date());
                ElasticSearchUtil.add(wechatIndex, movieType, movieQueryMessage);
            }
        }
        elasticSearchContent(newsList, content, false);
        //组装url
        packBusinessUserUrl(user, newsList);
        //组装广告
        if (newsList.size() > 0) {
            addAd(user, newsList);
            message = MessageUtil.packNewsMessage(toUserName, fromUserName, newsList);
        } else {
            message = emptyResultMessage(newsList, content, user, toUserName, fromUserName);
        }
        long spiderTimeEnd = System.currentTimeMillis();
        record.setContent(content);
        record.setSpiderTime(spiderTimeEnd - spiderTimeStart);
        ElasticSearchRecord elasticSearchRecord = new ElasticSearchRecord();
        elasticSearchRecord.setFromUserId(fromUserName);
        elasticSearchRecord.setMessageTime(new Date());
        elasticSearchRecord.setOwnerId(user.getUsername());
        elasticSearchRecord.setSentMessage(message);
        elasticSearchRecord.setReceivedMessage(content);
        ElasticSearchUtil.add(userIndex, recordType, elasticSearchRecord);
        return message;
    }

    private void packBusinessUserUrl(User user, List<News> newsList) {
        if (user.getStatus() == 3 && !user.getUsername().equals("anonymous")) {
            for (News n : newsList) {
                n.setUrl(WechatUtil.packUserUrl(n.getUrl(), user.getUserId()));
            }
        }
    }

    private void addAd(User user, List<News> newsList) {
        List<DefinedReply> adList;
        if ((adList = definedReplyService.getADList(user.getUsername())) != null) {
            if (adList.size() > 0 && adList.get(0).getValue() != null && !adList.get(0).getValue().equals("")) {
                DefinedReply d = adList.get(0);
                News news = new News();
                news.setDescription("");
                news.setTitle(d.getValue());
                news.setPicUrl(d.getPicUrl());
                news.setUrl(d.getUrl());
                newsList.add(1, news);
            }
        }
    }

    private void elasticSearchContent(List<News> newsList, String content, boolean containContent) {
        List<MovieQueryMessage> movieQueryMessages = ElasticSearchUtil.fuzzySearch(wechatIndex, movieType, "queryContent", content, containContent);
        Set<String> urlSet = new HashSet<>();
        for (News n : newsList)
            urlSet.add(n.getUrl());
        for (MovieQueryMessage movieQueryMessage : movieQueryMessages) {
            List<NewsPo> newsPos = JSON.parseArray(movieQueryMessage.getQueryResult(), NewsPo.class);
            if (newsList.size() < 8) {
                for (int i = 0; i < newsPos.size() && newsList.size() < 8; i++) {
                    News news = new News();
                    news.setTitle("相似匹配-" + newsPos.get(i).getTitle());
                    news.setDescription("相似匹配-" + newsPos.get(i).getDescription());
                    news.setUrl(newsPos.get(i).getUrl());
                    news.setPicUrl(newsPos.get(i).getPicUrl());
                    if (urlSet.add(news.getUrl()))
                        newsList.add(news);
                }
            } else
                break;
        }
    }

    public String emptyResultMessage(List<News> newsList, String content, User user, String toUserName, String fromUserName) {
        String message, reply;
        newsList.clear();
        elasticSearchContent(newsList, content, true);
        if (newsList.size() > 0) {
            News n = new News();
            n.setTitle("搜索不到您当前输入的信息，为您推荐以下相似内容");
            n.setUrl("http://www.baidu.com");
            newsList.add(0, n);
            packBusinessUserUrl(user, newsList);
            addAd(user, newsList);
            message = MessageUtil.packNewsMessage(toUserName, fromUserName, newsList);
        } else {
            if (definedReplyService.getReply("搜索不到的回复", user.getUsername()) != null)
                reply = definedReplyService.getReply("搜索不到的回复", user.getUsername()).getValue();
            else {
                reply = "您输入的消息为" + "'" + content + "'," + "搜索不到您当前输入的资源，请检查下您的片名是否正确";
            }
            if (reply == null || reply.equals("")) {
                reply = "您输入的消息为" + "'" + content + "'," + "搜索不到您当前输入的资源，请检查下您的片名是否正确";
            }
            message = MessageUtil.packText(toUserName, fromUserName, reply);
        }
        return message;
    }
}

package com.cjy.WechatHome.util;


import com.alibaba.fastjson.JSON;
import com.cjy.WechatHome.wechat.model.ElasticSearchRecord;
import com.cjy.WechatHome.wechat.model.MovieQueryMessage;
import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.apache.naming.SelectorContext.prefixLength;

public class ElasticSearchUtil {
    private final static int port = 9300;
    private final static String host = "101.132.158.220";
    private static Logger logger = Logger.getLogger(ElasticSearchUtil.class);
    private static TransportClient client;

    static {
        try {
            client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            //logger.info(client.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static List<MovieQueryMessage> fuzzySearch(String index, String type, String field, String content, boolean containContent) {
        List<MovieQueryMessage> results = new LinkedList<>();
        SearchRequestBuilder responsebuilder = client.prepareSearch(index).setTypes(type);
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery(field, content)
                .fuzziness(Fuzziness.TWO)
                .prefixLength(0).maxExpansions(100);
        responsebuilder.setQuery(fuzzyQueryBuilder);
        //SearchResponse searchResponse = responsebuilder.setFrom(0).setSize(10).setExplain(true).execute().actionGet();
        SearchResponse searchResponse = responsebuilder.get();
        SearchHits hits = searchResponse.getHits();
        Set<String> nameSet = new HashSet<>();
        if (!containContent) {
            nameSet.add(content);
        }
        for (int i = 0; i < hits.getHits().length; i++) {
            MovieQueryMessage movieQueryMessage = JSON.parseObject(hits.getHits()[i].getSourceAsString(), MovieQueryMessage.class);
            if (nameSet.add(movieQueryMessage.getQueryContent())&&movieQueryMessage.getQueryContent()!=null&&!movieQueryMessage.getQueryContent().equals("empty")) {
                results.add(movieQueryMessage);
            }
        }
        logger.info(content + "有效索引库的数据长度:" + (nameSet.size()-(containContent==true?0:1)));
        return results;
    }

    public static void add(String index, String type, MovieQueryMessage content) {
        IndexResponse response = null;
        try {
            response = client.prepareIndex(index, type).setSource(XContentFactory.jsonBuilder()
                    .startObject().field("queryUserId", content.getQueryUserId())
                    .field("ownerId", content.getOwnerId())
                    .field("queryContent", content.getQueryContent())
                    .field("queryResult", content.getQueryResult())
                    .field("queryTime", content.getQueryTime())
                    .endObject()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //logger.info(response);
    }

    public static void add(String index, String type, ElasticSearchRecord record) {
        IndexResponse response = null;
        try {
            response = client.prepareIndex(index, type).setSource(XContentFactory.jsonBuilder()
                    .startObject().field("fromUserId", record.getFromUserId())
                    .field("ownerId", record.getOwnerId())
                    .field("receivedMessage", record.getReceivedMessage())
                    .field("sentMessage", record.getSentMessage())
                    .field("messageTime", record.getMessageTime())
                    .endObject()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(response);
    }
}

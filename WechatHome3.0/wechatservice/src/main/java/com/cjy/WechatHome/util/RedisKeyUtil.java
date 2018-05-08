package com.cjy.WechatHome.util;


public class RedisKeyUtil {
	private static String SPLIT=":";
	private static String BIZ_EVENTQUEUE="EVENT_QUEUE";
    private static String VIEDO_PAGE_KEY="VIDEO_PAGE";
    private static String WECHAT_USER_KEY="WECHAT_USER";
    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }
    public static String getViedoPageKey() {
        return VIEDO_PAGE_KEY+SPLIT;
    }
    public static String getWechatUserKey() {
        return WECHAT_USER_KEY+SPLIT;
    }
}

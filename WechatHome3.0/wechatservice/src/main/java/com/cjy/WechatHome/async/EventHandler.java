package com.cjy.WechatHome.async;

import java.util.List;

/**
 * Created by cjy on 2017/7/30.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}

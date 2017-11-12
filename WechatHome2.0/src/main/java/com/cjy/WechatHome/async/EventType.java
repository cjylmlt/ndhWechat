package com.cjy.WechatHome.async;

/**
 * Created by nowcoder on 2016/7/30.
 */
public enum EventType {
    VIDEOSPIDER(0),
	TOPRECORD(1),
	MESSAGE(2);
    private int value;
    EventType(int value) { this.value = value; }
    public int getValue() { return value; }
}

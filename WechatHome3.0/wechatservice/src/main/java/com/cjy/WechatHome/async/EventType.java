package com.cjy.WechatHome.async;


public enum EventType {
    VIDEOSPIDER(0),
	TOPRECORD(1),
	MESSAGE(2),
	RENEWVIP(3);
    private int value;
    EventType(int value) { this.value = value; }
    public int getValue() { return value; }
}

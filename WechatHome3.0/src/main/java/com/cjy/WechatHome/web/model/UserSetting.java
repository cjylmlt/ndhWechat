package com.cjy.WechatHome.web.model;

public class UserSetting {
    private int id;
    private int registerTime;
    private int recommendTime;
    private int recommendNum;
    private String alipayKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(int registerTime) {
        this.registerTime = registerTime;
    }

    public int getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(int recommendTime) {
        this.recommendTime = recommendTime;
    }
    public int getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(int recommendNum) {
        this.recommendNum = recommendNum;
    }
    public String getAlipayKey() {
        return alipayKey;
    }

    public void setAlipayKey(String alipayKey) {
        this.alipayKey = alipayKey;
    }
}

package com.cjy.myWeb.po;

import java.util.Date;


public class User {
	private String id;
	private String username;
	private String password;
	private String nickname;
	private String truename;
	private String userImg;
	private Date birthday;
	private Integer gender;
	private String address;
	private String industry;
	private String career;
	private String email;
	private String telphone;
	private String qq;
	private String wechat;
	private String knowField;
	private String professionalSkill;
	private String province;
	private String city;
	private String introduction;
	private Integer visitedNum;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getKnowField() {
		return knowField;
	}
	public void setKnowField(String knowField) {
		this.knowField = knowField;
	}
	public String getProfessionalSkill() {
		return professionalSkill;
	}
	public void setProfessionalSkill(String professionalSkill) {
		this.professionalSkill = professionalSkill;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Integer getVisitedNum() {
		return visitedNum;
	}
	public void setVisitedNum(Integer visitedNum) {
		this.visitedNum = visitedNum;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", truename=" + truename + ", userImg=" + userImg + ", birthday=" + birthday + ", gender=" + gender
				+ ", address=" + address + ", industry=" + industry + ", career=" + career + ", email=" + email
				+ ", telphone=" + telphone + ", qq=" + qq + ", wechat=" + wechat + ", knowField=" + knowField
				+ ", professionalSkill=" + professionalSkill + ", province=" + province + ", city=" + city
				+ ", introduction=" + introduction + ", visitedNum=" + visitedNum + "]";
	}
	
}
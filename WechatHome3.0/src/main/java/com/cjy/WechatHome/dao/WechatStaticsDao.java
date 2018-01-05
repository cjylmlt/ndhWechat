package com.cjy.WechatHome.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.model.WechatStatics;

@Mapper
public interface WechatStaticsDao {
	String TABLE_NAME = " wechat_statics ";
	String INSERT_FIELDS=" date,new_user,cancel_user,netgain_user,cumulate_user ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
	String INSERT_VALUES="values(#{date},#{newUser},#{cancelUser},#{netgainUser},#{cumulateUser})";
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," order by date desc limit 10"})
	List<WechatStatics> selectFirstPage();
	@Select({"select date from ",TABLE_NAME," order by date asc limit 1"})
	Date selectStartDate();
	@Select({"select date from ",TABLE_NAME," order by date desc limit 1"})
	Date selectEndDate();
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	void insertStatics(WechatStatics wechatStatics);
}

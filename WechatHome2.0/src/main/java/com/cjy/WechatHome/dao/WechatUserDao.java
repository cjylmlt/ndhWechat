package com.cjy.WechatHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.model.WechatUser;

@Mapper
public interface WechatUserDao {
	String TABLE_NAME = " wechat_user ";
	String INSERT_FIELDS=" open_id,user_name,belong_owner_id,expire_time ";
	String SELECT_FIELDS=" open_id,user_name,belong_owner_id,expire_time ";
	String INSERT_VALUES="values(#{openId},#{userName},#{belongOwnerId},#{expireTime})";
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	int addWechatUser(WechatUser user);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where open_id = #{openId}"})
	WechatUser selectWechatById(String openId);
	@Update({"update",TABLE_NAME,"set expire_time=#{expireTime} where open_id=#{openId}"})
	void updateExpireTime(WechatUser user);
}

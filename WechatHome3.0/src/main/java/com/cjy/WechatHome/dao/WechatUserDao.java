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
	String INSERT_FIELDS=" open_id,user_name,belong_owner_id,expire_time,recommend_num ";
	String SELECT_FIELDS=" open_id,user_name,belong_owner_id,expire_time,recommend_num ";
	String INSERT_VALUES="values(#{openId},#{userName},#{belongOwnerId},#{expireTime},#{recommendNum})";
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	int addWechatUser(WechatUser user);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where open_id = #{openId}"})
	WechatUser selectWechatById(String openId);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where user_name=#{username}"})
	WechatUser selectWechatByName(String username);
	@Update({"update",TABLE_NAME,"set expire_time=#{expireTime} , recommend_num = #{recommendNum} where open_id=#{openId}"})
	void updateWechatUser(WechatUser user);
	@Delete({"delete from ",TABLE_NAME," where user_name=#{username}"})
	void deleteWechatUser(String username);
}

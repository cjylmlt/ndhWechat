package com.cjy.WechatHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cjy.wechathome.data.theater.Fan;

@Mapper
public interface FanDao {
	String TABLE_NAME = " fan ";
	String INSERT_FIELDS=" open_id,user_name,belong_owner_id,expire_time,recommend_num ";
	String SELECT_FIELDS=" open_id,user_name,belong_owner_id,expire_time,recommend_num ";
	String INSERT_VALUES="values(#{openId},#{userName},#{belongOwnerId},#{expireTime},#{recommendNum})";
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	int addFan(Fan user);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where open_id = #{openId}"})
	Fan selectFanById(String openId);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where user_name=#{username}"})
	Fan selectFanByName(String username);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where belong_owner_id = #{0} order by recommend_num desc limit #{1},#{2} "})
	List<Fan> selectFansByOwner(String ownerOpenId,int offset, int limit);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where user_name=#{username} and belong_owner_id = #{ownerOpenId}"})
	Fan selectFanByNameAndOwner(String username,String ownerOpenId);
	@Select({"select count(*) from",TABLE_NAME,"where belong_owner_id = #{ownerOpenId}"})
	int selectCountByOwner(String ownerOpenId);
	@Update({"update",TABLE_NAME,"set expire_time=#{expireTime} , recommend_num = #{recommendNum} where open_id=#{openId}"})
	void updateFan(Fan fan);
	@Delete({"delete from ",TABLE_NAME," where user_name=#{username}"})
	void deleteFan(String username);
}

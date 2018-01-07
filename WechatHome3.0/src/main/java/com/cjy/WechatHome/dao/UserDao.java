package com.cjy.WechatHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cjy.WechatHome.web.model.User;

@Mapper
public interface UserDao {
	String TABLE_NAME = " user ";
	String INSERT_FIELDS=" username,userId,status,chineseName,noteOne,password,salt,headUrl ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
	String INSERT_VALUES="values(#{username},#{userId},#{status},#{chineseName},#{noteOne},#{password},#{salt},#{headUrl})";
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	int addUser(User user);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id}"})
	User selectById(int id);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where username = #{name}"})
	User selectByName(String name);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME})
	List<User> selectAll();
	@Update({"update",TABLE_NAME,"set password=#{password} where id=#{id}"})
	void updatePassword(User user);
	@Update({"update",TABLE_NAME,"set username=#{username},userId=#{userId},status=#{status},chineseName=#{chineseName},noteOne=#{noteOne},password=#{password},salt=#{salt},headUrl=#{headUrl} where id=#{id}"})
	void updateUser(User user);
	@Delete({"delete from",TABLE_NAME,"where id=#{id}"})
	void deleteById(int id);
	
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where userId = #{userId}"})
	User selectUserByUserId(String userId);
	
}

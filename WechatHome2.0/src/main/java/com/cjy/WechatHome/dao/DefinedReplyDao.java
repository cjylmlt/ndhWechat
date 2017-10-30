package com.cjy.WechatHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cjy.WechatHome.model.DefinedReply;
@Mapper
public interface DefinedReplyDao {
	String TABLE_NAME = " defined_reply ";
	String INSERT_FIELDS=" replyKey,value,picUrl,url,username ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
	String INSERT_VALUES="values(#{replyKey},#{value},#{picUrl},#{url},#{userName})";
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where replyKey = #{0} and username = #{1}"})
	public DefinedReply getDefinedReplyByKeyAndUser(String replyKey,String username);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where replyKey = '广告' and username = #{0}"})
	public List<DefinedReply> getAdByUser(String username);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where username = #{0}"})
	public List<DefinedReply> getDefinedReplyByUser(String username);
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	public void insertDefinedReply(DefinedReply definedReply);
	@Delete({"delete from",TABLE_NAME,"where `id` = #{id}"})
	public void deleteDefinedReply(int id);
	
}
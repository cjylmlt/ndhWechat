package com.cjy.WechatHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import  com.cjy.WechatHome.model.Record;
import com.cjy.WechatHome.model.TopRecord;
@Mapper
public interface RecordDao{
	String TABLE_NAME = " user_record ";
	String INSERT_FIELDS=" username,content,reply,userUrl,userId,status,durationTime,spiderTime,date ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
	String INSERT_VALUES="values(#{username},#{content},#{reply},#{userUrl},#{userId},#{status},#{durationTime},#{spiderTime},#{date})";
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	void insertRecord(Record record);
	 
	@Select({"select content,count(*) as count from ",TABLE_NAME," where content!='VIEW' and content!='subscribe' and content!='unsubscribe' ","group by content order by count desc limit 10"})
	List<TopRecord> selectTopRecord();
	
}
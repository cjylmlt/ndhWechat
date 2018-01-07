package com.cjy.WechatHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cjy.WechatHome.web.model.Record;
import com.cjy.WechatHome.web.model.TopRecord;
@Mapper
public interface TopRecordDao{
	String TABLE_NAME = " top_record ";
	String INSERT_FIELDS=" content,count,update_time,status ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
	String INSERT_VALUES="values(#{content},#{count},#{updateTime},#{status})";
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	void insertTopRecord(TopRecord topRecord);
	 
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where status = '0' and content!='subscribe' and content!='unsubscribe' order by count desc limit 10"})
	List<TopRecord> selectTopRecord();
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where status = '0' and content=#{subOrUnsub}"})
	TopRecord selectSubRecord(String subOrUnsub);
	
	@Update({"update ",TABLE_NAME," set status = '1' where status = '0' and content!='subscribe' and content!='unsubscribe'"})
	void DisableTopRecord();
	@Update({"update ",TABLE_NAME," set status = '1' where content='subscribe' "})
	void DisableSubRecord();
	@Update({"update ",TABLE_NAME," set status = '1' where content='unsubscribe' "})
	void DisableUnsubRecord();
}

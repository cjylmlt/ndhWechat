package com.cjy.WechatHome.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminSettingDao{
	String TABLE_NAME = " admin_setting ";
	String INSERT_FIELDS=" key,value ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
	String INSERT_VALUES=" values(#{key},#{value})";
	@Select({"SELECT value FROM `admin_setting` WHERE `key` = #{key}"})
	String selectAdminSetting(String key);
	@Update({"update `admin_setting` set `value` = #{1} WHERE `key` = #{0}"})
	void updateAdminSetting(String key,String value);

}

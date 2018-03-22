package com.cjy.WechatHome.dao;


import com.cjy.WechatHome.web.model.UserSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserSettingDao {
	String TABLE_NAME = " user_setting ";
	String INSERT_FIELDS=" id,register_time,recommend_time,recommend_num,alipay_key ";
	String SELECT_FIELDS=  INSERT_FIELDS;
	String INSERT_VALUES=" values(#{id},#{registerTime},#{recommendTime},#{recommendNum},#{alipayKey}) ";
	@Select({"SELECT",SELECT_FIELDS, " FROM ",TABLE_NAME,"where id=#{id}"})
	UserSetting selectUserSetting(int userId);
	@Update({"update ",TABLE_NAME,"set register_time=#{registerTime},recommend_time=#{recommendTime},recommend_num=#{recommendNum},alipay_key=#{alipayKey} where id=#{id}"})
	void updateUserSetting(UserSetting userSetting);
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	void insertUserSetting(UserSetting userSetting);

}

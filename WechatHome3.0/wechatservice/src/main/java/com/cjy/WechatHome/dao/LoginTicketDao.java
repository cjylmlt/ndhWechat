package com.cjy.WechatHome.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import com.cjy.wechathome.data.web.LoginTicket;

@Mapper
public interface LoginTicketDao {
	String TABLE_NAME = " login_ticket ";
	String INSERT_FIELDS=" user_id,ticket,expired,status ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
	String INSERT_VALUES="values(#{userId},#{ticket},#{expired},#{status})";
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")",INSERT_VALUES})
	int addTicket(LoginTicket ticket);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where ticket = #{ticket}"})
	LoginTicket selectByTicket(String ticket);
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where user_id = #{id} limit 2"})
	List<LoginTicket> selectById(String id);
	@Update({"update",TABLE_NAME,"set status=#{0} where ticket=#{1}"})
	void updateStatus(int status,String ticket);

	
}

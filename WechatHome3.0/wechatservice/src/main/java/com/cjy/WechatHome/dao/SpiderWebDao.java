package com.cjy.WechatHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cjy.wechathome.data.theater.SpiderWeb;

@Mapper
public interface SpiderWebDao{
	String TABLE_NAME = " spider_web ";
	String INSERT_FIELDS=" webUrl,searchUrl,titleRegex1,picRegex1,videoRegex1,titleRegex2,picRegex2,videoRegex2,userId,webName ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
	String INSERT_VALUES="values(#{webUrl},#{searchUrl},#{titleRegex1},#{picRegex1},#{videoRegex1},#{titleRegex2},#{picRegex2},#{videoRegex2},#{userId},#{webName}})";
	 
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME })
	List<SpiderWeb> selectWebList();
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id = '0' "})
	SpiderWeb selectPriorWeb();
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id = #{0} "})
	SpiderWeb selectUserWeb(int userId);
	@Update({"update ",TABLE_NAME," set webUrl=#{0} where id = #{1} "})
	void updateWebUrl(String webUrl,int id);
}

package com.cjy.WechatHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cjy.WechatHome.model.News;
import com.cjy.WechatHome.model.NewsPo;


@Mapper
public interface NewsDao {
	String TABLE_NAME = " `news_po` ";
	String INSERT_PO_FIELDS=" `key`,`Title`,`Description`,`PicUrl`,`Url`,`updateTime` ";
	String SELECT_PO_FIELDS=" `id`,`key`,`Title`,`Description`,`PicUrl`,`Url`,`updateTime` ";
	String SELECT_FIELDS=" `id`,`Title`,`Description`,`PicUrl`,`Url` ";
	String INSERT_PO_VALUES="values(#{key},#{Title},#{Description},#{PicUrl},#{Url},#{updateTime})";
	@Select({"select ", SELECT_FIELDS,"from",TABLE_NAME,"where `key` = #{key} "})
	public List<News> selectNewsByKey(String key);
	@Select({"select ", SELECT_PO_FIELDS,"from",TABLE_NAME,"where `key` = #{key} "})
	public List<NewsPo> selectNewsPoByKey(String key);
	@Insert({"insert into",TABLE_NAME,"(",INSERT_PO_FIELDS,")",INSERT_PO_VALUES})
	public void insertNews(NewsPo newsPo);
	@Delete({"delete from",TABLE_NAME,"where `key` = #{key}"})
	public void deleteNewsByKey(String key);
}

package com.cjy.wenda.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cjy.imooc.mapper.DefinedReplyMapper;
import com.cjy.imooc.mapper.NewsMapper;
import com.cjy.imooc.mapper.RecordMapper;
import com.imooc.po.DefinedReply;
import com.imooc.po.News;
import com.imooc.po.NewsPo;
import com.imooc.po.Record;
import com.imooc.util.NewsUtil;

import junit.framework.TestCase;



public class MybatisTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException{
//		String resource = "mybatis-config.xml";
//		InputStream inputStream = Resources.getResourceAsStream(resource);
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		DefinedReplyMapper mapper = sqlSession.getMapper(DefinedReplyMapper.class);
////		DefinedReply definedReply = mapper.getDefinedReplyByKeyAndUser("","nhd");
////		System.out.println(definedReply.getValue());
////		System.out.println(definedReply.getUrl());
////		System.out.println(definedReply.getPicUrl());
//		
//		List<DefinedReply> list = mapper.getAdByUser("nhd");
//		for(DefinedReply d:list){
//			System.out.println(d.getValue());
//			System.out.println(d.getUrl());
//			System.out.println(d.getPicUrl());
//		}
//		String resource = "mybatis-config.xml";
//		InputStream inputStream = Resources.getResourceAsStream(resource);
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		RecordMapper mapper = sqlSession.getMapper(RecordMapper.class);
//		Record record = new Record();
//		record.setUserId("11");
//		record.setUsername("");
//		record.setDate(new Date());
//		mapper.insertRecord(record);
//		sqlSession.commit();

		NewsUtil.deleteNewsByKey("a");
		
	}

}

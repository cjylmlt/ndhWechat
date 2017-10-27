package com.imooc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cjy.imooc.mapper.DefinedReplyMapper;
import com.cjy.imooc.mapper.UserMapper;
import com.imooc.po.DefinedReply;
import com.imooc.po.User;

public class DefinedReplyUtil {
	
	public static DefinedReply getReply(String replyKey,String username) throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		DefinedReplyMapper mapper = sqlSession.getMapper(DefinedReplyMapper.class);
		DefinedReply definedReply = mapper.getDefinedReplyByKeyAndUser(replyKey,username);
		if (definedReply!=null) {
			System.out.println(definedReply.getValue());
		}
		return definedReply;
	}
	public static List<DefinedReply> getADList(String username) throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		DefinedReplyMapper mapper = sqlSession.getMapper(DefinedReplyMapper.class);
		List<DefinedReply> adList = mapper.getAdByUser(username);
		if (adList!=null) {
			
		}
		return adList;
	}
	public static String getUserName(String userUrl) throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user =userMapper.selectUserByUrl(userUrl);
		if(user!=null)
			return user.getUsername();
		else
			return null;
	}
}

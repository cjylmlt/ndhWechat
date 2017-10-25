package com.cjy.imooc.mapper;

import java.util.List;

import com.imooc.po.DefinedReply;

public interface DefinedReplyMapper {
	
	public DefinedReply getDefinedReplyByKeyAndUser(String replyKey,String username);
	public List<DefinedReply> getAdByUser(String username);
	
}

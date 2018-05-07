package com.cjy.WechatHome.dao;

import org.apache.ibatis.annotations.*;

import com.cjy.WechatHome.theater.model.Message;

import java.util.List;

/**
 * Created by cjy on 2017/7/24.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Select({"select ", INSERT_FIELDS, " , count(id) as id from ( select * from ", TABLE_NAME,
            " where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") String userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);
    
    @Select({"select ", INSERT_FIELDS, " from ", TABLE_NAME , " where from_id=#{userId} or to_id=#{userId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getMessageList(@Param("userId") String userId,
                                @Param("offset") int offset,
                                @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") String userId, @Param("conversationId") String conversationId);
    
    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} "})
    int getUnreadCount(@Param("userId") String userId);
    
    @Update({"update",TABLE_NAME,"set has_read=1 where to_id=#{userId}"})
	void updateReadStatus(String userId);
    
    @Delete({"delete from ",TABLE_NAME," where to_id=#{userId}"})
	void deleteMessageByUserId(String userId);
}

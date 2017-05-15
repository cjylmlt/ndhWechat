package com.cjy.myWeb.mapper;

import java.util.List;

import com.cjy.myWeb.po.User;

public interface UserMapper {
	public User findUserById(String id);//ok
	public List<User> findUserListByUsername(String username);
	public User findUserByUsername(String username);//ok
	public int insert(User user);
	public int deleteUserById(String id);
	public int updateUserById(String id);
}

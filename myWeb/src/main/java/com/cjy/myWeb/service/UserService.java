package com.cjy.myWeb.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjy.myWeb.po.User;

public interface UserService {
	public boolean checkCheckCode(HttpServletRequest request,HttpServletResponse response) throws Exception;
	public void printMessage(HttpServletRequest request,HttpServletResponse response,MessageType errorType);
	public boolean checkLoginEmpty(HttpServletRequest request, HttpServletResponse response);
	public boolean checkRegisterEmpty(HttpServletRequest request,HttpServletResponse response);
	public boolean checkLoginPassword(HttpServletRequest request,HttpServletResponse response);
	public boolean registerNewUser(HttpServletRequest request,HttpServletResponse response);
	public User findUserByUsername(String username);
}

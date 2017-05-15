package com.cjy.myWeb.service.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.cjy.myWeb.mapper.UserMapper;
import com.cjy.myWeb.po.User;
import com.cjy.myWeb.service.MessageType;
import com.cjy.myWeb.service.UserService;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	public boolean checkCheckCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userValidateCode = request.getParameter("checkCode");
		String correctValidateCode = (String) request.getSession().getAttribute("checkCode");
		if (userValidateCode != null) {
			if (userValidateCode.equals("")) {
				System.out.println("验证码为空");
				printMessage(request, response, MessageType.emptyCheckCode);
				return false;
			} else if (userValidateCode.equals(correctValidateCode)) {
				System.out.println("验证码正确");
				return true;
			} else {
				System.out.println("验证码错误");
				printMessage(request, response, MessageType.errorCheckCode);
				return false;
			}
		} else
			return false;
	}

	public void printMessage(HttpServletRequest request, HttpServletResponse response, MessageType errorType) {
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html;charset=UTF-8");
			switch (errorType) {
			case emptyCheckCode:
				out.println("<script>alert('验证码不能为空');history.back();</script>");
				break;
			case emptyPassword:
				out.println("<script>alert('密码不能为空');history.back();</script>");
				break;
			case emptyUserName:
				out.println("<script>alert('用户名不能为空');history.back();</script>");
				break;
			case errorCheckCode:
				out.println("<script>alert('验证码不正确');history.back();</script>");
				break;
			case errorPassword:
				out.println("<script>alert('密码不正确');history.back();</script>");
			case errorConfirmPassword:
				out.println("<script>alert('两次密码不同');history.back();</script>");
				break;
			case userExit:
				out.println("<script>alert('该用户名已经存在');history.back();</script>");
				break;
			case loginSuccess:
				out.println("<script>alert('登陆成功'));</script>");
				break;
			default:
				break;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkLoginEmpty(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if (userName == null) {
			printMessage(request, response, MessageType.emptyUserName);
			return false;
		} else if (password == null) {
			printMessage(request, response, MessageType.emptyPassword);
			return false;
		} else {
			return true;
		}
	}

	public boolean checkRegisterEmpty(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String userValidateCode = request.getParameter("checkCode");
		String correctValidateCode = (String) request.getSession().getAttribute("checkCode");
		String password = request.getParameter("password");
		String comfirmPassword = request.getParameter("confirmPassword");
		if (userValidateCode != null && correctValidateCode != null && password != null && comfirmPassword != null) {
			// check empty
			if (username == "") {
				printMessage(request, response, MessageType.emptyUserName);
				return false;
			} else if (password == "" || comfirmPassword == "") {
				printMessage(request, response, MessageType.emptyPassword);
				return false;
			} else if (!password.equals(comfirmPassword)) {
				printMessage(request, response, MessageType.errorConfirmPassword);
				return false;
			}
		} else
			return false;
		return true;
	}

	@Override
	public boolean checkLoginPassword(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password"); 
		User user = userMapper.findUserByUsername(username);
		if(user==null)
			return false;
		else{
			if(password.equals(user.getPassword()))
				return true;
			else
				return false;
		}
	}

	@Override
	public boolean registerNewUser(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userMapper.findUserByUsername(username);
		if(user==null){
			user = new User();
			user.setId(username);
			user.setUsername(username);
			user.setPassword(password);
			userMapper.insert(user);
			return true;
		}
		else{//用户已存在
			return false;
		}
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userMapper.findUserByUsername(username);
	}
}

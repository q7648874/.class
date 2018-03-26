package com.product.service.entity;

import java.sql.SQLException;

import com.product.dao.UserDao;
import com.product.dao.entity.UserDaoEntity;
import com.product.domain.User;
import com.product.service.UserService;
import com.product.util.SendJMail;

public class UserServiceEntity implements UserService{
	private UserDao ud = new UserDaoEntity();
	@Override//注册的同时发送激活邮件
	public void register(User u) {
		// TODO Auto-generated method stub
		try {
			ud.insert(u);
			String eamil = u.getEmail();
			SendJMail.sendMail(eamil, "<a href='http://localhost:8080/product/userServlet?method=activeCode&code="+u.getCode()+"'>点此激活</a>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override//激活
	public void activeCode(String code) {
		// TODO Auto-generated method stub
		try {
			User user = ud.queryOne("code", code);
			user.setState(1);
			ud.update(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		try {
			User u = ud.queryOne("username", username);
			if(u==null){
				return null;
			}
			String uPassword = u.getPassword();
			if(password.equals(uPassword)){
				return u;
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public User ckusername(String username) {
		// TODO Auto-generated method stub
		try {
			return ud.queryOne("username", username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

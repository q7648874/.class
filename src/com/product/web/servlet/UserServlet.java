package com.product.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.product.domain.User;
import com.product.service.UserService;
import com.product.service.entity.UserServiceEntity;
import com.product.web.base.BaseServlet;

import cn.dsna.util.images.ValidateCode;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	//ע��
	public String register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			User u = new User();
			ConvertUtils.register(new DateLocaleConverter() , Date.class); 
			BeanUtils.populate(u, req.getParameterMap());
			u.setUid(UUID.randomUUID().toString());
			u.setCode(UUID.randomUUID().toString());
//			System.out.println(u);
			UserService us = new UserServiceEntity();
			us.register(u);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/user/login.jsp";
	}
	//����
	public String activeCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		UserService us = new UserServiceEntity();
		us.activeCode(code);
		return "/user/login.jsp";
	}
	//��¼
	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String yzm = req.getParameter("yzm");
		HttpSession session = req.getSession();
		String code = (String) session.getAttribute("sess_code");
		if(!code.equalsIgnoreCase(yzm)){
			req.setAttribute("msg", "��֤������");
			return "/user/login.jsp";
		}
		UserService us = new UserServiceEntity();
		User u = us.login(username,password);
		if(u==null){
			req.setAttribute("msg", "�û���������������");
			return "/user/login.jsp";
		}
		if(u.getState()==0){
			req.setAttribute("msg", "�û���δ����");
			return "/user/login.jsp";
		}
		String auto = req.getParameter("autologin");
		Cookie autocookie = new Cookie("autoLogin", u.getUsername()+"-"+u.getPassword());
		if(auto==null){
			autocookie.setMaxAge(0);//ֱ��ɱ��
		}else{
			autocookie.setMaxAge(Integer.MAX_VALUE);//���ñ���
		}
		resp.addCookie(autocookie);
		session.setAttribute("sess_user", u);
		return "/user/index.jsp";
	}
	//��֤��
	public String validateCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ValidateCode vc = new ValidateCode(200, 30, 2, 14);
		req.getSession().setAttribute("sess_code", vc.getCode());
		vc.write(resp.getOutputStream());
		return null;
	}
	//�ǳ�
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if("autoLogin".equals(cookie.getName())){
				cookie.setMaxAge(0);
				resp.addCookie(cookie);
				break;
			}
		}
		return "/user/index.jsp";
	}
	//ajax�첽�ύУ���û���
	public String ckusername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		UserService us = new UserServiceEntity();
		User u = us.ckusername(username);
		System.out.println(u);
		if(u==null){
			resp.getWriter().println(true);
		}else{
			resp.getWriter().println(false);
		}
		return null;
	}
	
}

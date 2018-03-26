package com.product.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			req.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=UTF-8");
			//�õ�������
			String method = req.getParameter("method");
			//�õ���ǰ����ֽ������
			Class clazz = this.getClass();
			//�õ�ָ���ķ���
			Method m = clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			//ִ��m����
			String path = (String) m.invoke(this, req,resp);
			//4�ַ�ת��
			if(path!=null){
				req.getRequestDispatcher(path).forward(req, resp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
}

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
			//得到方法名
			String method = req.getParameter("method");
			//得到当前类的字节码对象
			Class clazz = this.getClass();
			//得到指定的方法
			Method m = clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			//执行m方法
			String path = (String) m.invoke(this, req,resp);
			//4分发转向
			if(path!=null){
				req.getRequestDispatcher(path).forward(req, resp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
}

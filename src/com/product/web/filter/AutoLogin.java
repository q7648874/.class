package com.product.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.product.domain.User;
import com.product.service.entity.UserServiceEntity;

/**
 * Servlet Filter implementation class AutoLogin
 */
public class AutoLogin implements Filter {

    /**
     * Default constructor. 
     */
    public AutoLogin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		//首先 强转请求对象
		HttpServletRequest request = (HttpServletRequest) req;
		//判断session域中是否有用户已经登录的信息
		User user = (User) request.getSession().getAttribute("sess_user");
		if(user==null){
			Cookie[] cookies = request.getCookies();
			if(cookies==null){//没有cookies数据 直接放行
				chain.doFilter(request, response);
				return;
			}
			Cookie autoCookie = null;//首先设置自动登录的cookie为null
			for (Cookie cookie : cookies) {//遍历所有cookies数组  寻找有没有自动登录的cookie
				if("autoLogin".equals(cookie.getName())){
					autoCookie = cookie;
					break;
				}
			}
			if(autoCookie==null){//如果没找到 放行
				chain.doFilter(request, response);
				return;
			}
			//找到了自动登录的cookie
			String[] values = autoCookie.getValue().split("-");
			user = new UserServiceEntity().login(values[0], values[1]);
			request.getSession().setAttribute("sess_user", user);
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

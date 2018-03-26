package com.product.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
	private static ThreadLocal<Connection> t = new ThreadLocal<Connection>();//con1
	/*
	 * 保证一个用户每次获取的连接都是同一个
	 * 保证不同用户不能获取相同的连接
	 */
	public static Connection getConnection(){
		Connection con = t.get();
		if(con == null){
			//到连接池中获取一个连接
			Connection con1 = C3P0util.getConnection();
			//将连接放入到容器
			t.set(con1);
			//将连接返回
			return con1;
		}
		return con;
	}
	
	//开启事务
	public static void begin(){
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//提交事务
	public static void commit(){
		try {
			getConnection().commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void rollBack(){
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(){
		try {
			getConnection().close();
			//从容器中清除连接
			t.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.product.domain;

import java.util.ArrayList;
import java.util.List;

//oidvarchar(50) NOT NULL
//ordertimedatetime NULL
//totaldouble NULL
//stateint(11) NULL
//addressvarchar(30) NULL
//namevarchar(20) NULL
//telephonevarchar(20) NULL
//uidvarchar(50) NULL
public class Order {
	private String oid;                                      //订单id
	private String ordertime;                                //创建订单的时间
	private double total;                                    //
	private int state;                                       //订单的状态
	private String address;                                  //地址
	private String name;                                     //被邮寄人的姓名
	private String telephone;                                //被邮寄人的电话
	private String uid;                                      //登录用户的id
	private List<Orderitem> orderitem = new ArrayList<>(0);  //封装了多个订单项的集合
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<Orderitem> getOrderitem() {
		return orderitem;
	}
	public void setOrderitem(List<Orderitem> orderitem) {
		this.orderitem = orderitem;
	}
	public Order(String oid, String ordertime, double total, int state, String address, String name, String telephone,
			String uid, List<Orderitem> orderitem) {
		super();
		this.oid = oid;
		this.ordertime = ordertime;
		this.total = total;
		this.state = state;
		this.address = address;
		this.name = name;
		this.telephone = telephone;
		this.uid = uid;
		this.orderitem = orderitem;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state + ", address="
				+ address + ", name=" + name + ", telephone=" + telephone + ", uid=" + uid + ", orderitem=" + orderitem
				+ "]";
	}
	
}

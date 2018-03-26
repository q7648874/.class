package com.product.domain;

import java.io.Serializable;
import java.util.Date;

//`uid` VARCHAR(50) ,
//`username` VARCHAR(20) ,
//`password` VARCHAR(20) ,
//`name` VARCHAR(20) ,
//`email` VARCHAR(30),
//`birthday` DATE ,
//`sex` VARCHAR(10), 
//`code` VARCHAR(50),		-- ¼¤»îÂë
//`state` INT(11) DEFAULT 0,  -- ¼¤»î×´Ì¬
public class User implements Serializable{
	private String uid;
	private String username;
	private String password;
	private String name;
	private String email;
	private Date birthday;
	private String sex;
	private String code;
	private int state;
	
	//ÐÂÔö½ÇÉ«×Ö¶Î
	private String role;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", name=" + name + ", email="
				+ email + ", birthday=" + birthday + ", sex=" + sex + ", code=" + code + ", state=" + state + ", role="
				+ role + "]";
	}
}

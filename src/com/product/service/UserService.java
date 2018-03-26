package com.product.service;

import com.product.domain.User;

public interface UserService {

	void register(User u);

	void activeCode(String code);

	User login(String username, String password);

	User ckusername(String username);

}

package com.ac.aircamp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ac.aircamp.model.User;

@Mapper
public interface UserDao {

	User checkUser(String u_id);
	int insert(User user);
	int insertSocial(User user);
	int editUser(User user);
	void update(User user);
	int withdraw(String u_id);
	User getUserDoEm(String userEmail, String userDomain);
	User findPwd(User user);
	void updatePwd(User user);
	public int pwdUpdate(User user);

	
	
}

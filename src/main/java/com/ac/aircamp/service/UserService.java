package com.ac.aircamp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ac.aircamp.dao.TourDao;
import com.ac.aircamp.dao.UserDao;
import com.ac.aircamp.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserDao userDao;
	
	//로그인 인증
	public User checkUser(String u_id) {
		return userDao.checkUser(u_id);
	}
	
	// ID중복 체크
	public int checkUserId(String u_id) {
		int result = -1; // 사용가능
		User user = userDao.checkUser(u_id);
		if(user != null) {
			result = 1; // 1은 사용 불가
		}
		return result;
	}
	
	// 비번 변경
	public int pwdUpdate(User user) {
		return userDao.pwdUpdate(user);
	}

	//회원가입- 일반유저 
	public int insert(User user) {
	
		return userDao.insert(user);
	}
	
	//회원가입- 소셜로그인
	public int insertSocial(User user) {
		
		return userDao.insertSocial(user);
	}
	
	//회원정보 수정
	public void update(User user) {
		userDao.update(user);
	}
	
	//회원탈퇴
	public int withdraw(String u_id) {
		int result = userDao.withdraw(u_id);
		return result;
		
	}

	// 유저 도메인 비밀번호 가져오기
	public User getUserDoEm(String userEmail, String userDomain) {
		return userDao.getUserDoEm(userEmail, userDomain);
	}

	// 유저 비밀번호 찾기
	public User findPwd(User user) {
		return userDao.findPwd(user);
	}

	public void updatePwd(User user) {
		userDao.updatePwd(user);
	}




	
	
	
	// 
	
}

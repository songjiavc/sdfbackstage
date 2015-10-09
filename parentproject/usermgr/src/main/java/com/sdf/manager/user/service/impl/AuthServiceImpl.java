package com.sdf.manager.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.user.entity.Authority;
import com.sdf.manager.user.repository.AuthRepository;
import com.sdf.manager.user.service.AuthService;

/** 
  * @ClassName: AuthServiceImpl 
  * @Description: 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:51:54 
  *  
  */
@Service("authService")
@Transactional(propagation=Propagation.REQUIRED)
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthRepository authRepository;
	
	/**
	 * @return
	 */
	public List<Authority> findAll() {
		return authRepository.findAll();
	}

}

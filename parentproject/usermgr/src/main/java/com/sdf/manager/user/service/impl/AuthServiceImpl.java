package com.sdf.manager.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.user.bean.AuthorityBean;
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

	/**
	 * 
	* @Description: TODO(保存权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午2:33:45
	 */
	public void save(Authority authority) {
		authRepository.save(authority);
		
	}

	
	/**
	 * 
	* @Description: TODO(根据code获取权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午3:37:34
	 */
	public Authority getAuthorityByCode(String code)
	{
		Authority authority =  authRepository.getAuthorityByCode(code);
		
		return authority;
	}
	
	/***
	 * 
	* @Description: TODO(获取权限数据列表) 
	* @author bann@sdfcp.com
	* @date 2015年10月10日 下午3:04:33
	 */
	public List<Authority> getAuthorityList(AuthorityBean authorityBean)
	{
		List<Authority> authorities = new ArrayList<Authority>();
		
		authorities = authRepository.findAll();
		
			
		return authorities;
	}
	
	
	
	
	
	
	
	

}

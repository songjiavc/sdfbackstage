package com.sdf.manager.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.user.bean.AuthorityBean;
import com.sdf.manager.user.entity.User;
import com.sdf.manager.user.repository.UserRepository;
import com.sdf.manager.user.service.UserService;

/** 
  * @ClassName: AuthServiceImpl 
  * @Description: 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:51:54 
  *  
  */
@Service("userService")
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * @return
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * 
	* @Description: TODO(保存权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午2:33:45
	 */
	public void save(User user) {
		userRepository.save(user);
		
	}

	
	/* (非 Javadoc) 
	 * <p>Title: getUserByCode</p> 
	 * <p>Description: </p> 
	 * @param code
	 * @return 
	 * @see com.sdf.manager.user.service.UserService#getUserByCode(java.lang.String) 
	 */
	public User getUserByCode(String code)
	{
		User user =  userRepository.getUserByCode(code);
		return user;
	}
	
	/***
	 * 
	* @Description: TODO(获取权限数据列表) 
	* @author bann@sdfcp.com
	* @date 2015年10月10日 下午3:04:33
	 */
	public List<User> getAuthorityList(AuthorityBean authorityBean)
	{
		List<User> userList = new ArrayList<User>();
		
		userList = userRepository.findAll();
		
			
		return userList;
	}

}

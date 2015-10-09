package com.sdf.manager.user.service;

import java.util.List;

import com.sdf.manager.user.entity.Authority;

/** 
  * @ClassName: AuthService 
  * @Description: 权限
  * @author songj@sdfcp.com
  * @date 2015年9月24日 上午11:20:03 
  *  
  */
public interface AuthService {
	
	/** 
	  * @Description: 获取权限表所有数据
	  * @author songj@sdfcp.com
	  * @date 2015年9月25日 上午8:58:51 
	  * @return 
	  */
	public List<Authority> findAll();
}

package com.sdf.manager.user.repository;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.user.entity.LoginStatus;

/** 
  * @ClassName: AuthRepository 
  * @Description: 权限 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:24:21 
  *  
  */
public interface LoginStatusRepostitory extends GenericRepository<LoginStatus, String>  {
	
}

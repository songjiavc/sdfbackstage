package com.sdf.manager.user.service;

import java.util.List;

import com.sdf.manager.user.bean.AccountBean;
import com.sdf.manager.user.entity.User;


/** 
  * @ClassName: UserService 
  * @Description:  
  * @author songj@sdfcp.com
  * @date 2015年10月12日 上午11:00:15 
  *  
  */
public interface UserService {
	
	/** 
	  * @Description: 获取权限表所有数据
	  * @author songj@sdfcp.com
	  * @date 2015年9月25日 上午8:58:51 
	  * @return 
	  */
	public List<User> findAll();

	/** 
	  * @Description: 保存用户信息
	  * @author songj@sdfcp.com
	  * @date 2015年10月12日 上午11:16:13 
	  * @param user 
	  */
	public void save(User user);

	/** 
	  * @Description: 通过code获得帐号信息
	  * @author songj@sdfcp.com
	  * @date 2015年10月12日 上午11:16:31 
	  * @param code
	  * @return 
	  */
	public User getUserByCode(String code);

}

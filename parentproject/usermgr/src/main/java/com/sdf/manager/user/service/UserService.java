package com.sdf.manager.user.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sdf.manager.common.util.QueryResult;
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

	/** 
	  * @Description:分页查询
	  * @author songj@sdfcp.com
	  * @date 2015年10月13日 下午1:25:25 
	  * @param whereJpql
	  * @param queryParams
	  * @param orderby
	  * @param pageable
	  * @return 
	  */
	public QueryResult<User> getScrollDataByJpql(String whereJpql, Object[] queryParams,LinkedHashMap<String, String> orderby, Pageable pageable);
}

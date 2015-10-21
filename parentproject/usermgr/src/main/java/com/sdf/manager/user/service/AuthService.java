package com.sdf.manager.user.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.user.bean.AuthorityBean;
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

	/**
	 * 
	* @Description: TODO(保存权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午2:35:42
	 */
	public void save(Authority authority);

	/**
	 * 
	* @Description: TODO(根据code获取权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午3:37:34
	 */
	public Authority getAuthorityByCode(String code);
	
	
	/***
	 * 
	* @Description: TODO(获取权限数据列表) 
	* @author bann@sdfcp.com
	* @date 2015年10月10日 下午3:04:33
	 */
	public List<Authority> getAuthorityList(AuthorityBean authorityBean);

	/**
	 * 
	* @Description: TODO(带分页条件模糊查询权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月14日 上午8:55:41
	 */
	public QueryResult<Authority> getAuthList(Class<Authority> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
}

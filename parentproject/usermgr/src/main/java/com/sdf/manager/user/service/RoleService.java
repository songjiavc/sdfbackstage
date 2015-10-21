package com.sdf.manager.user.service;

import java.util.LinkedHashMap;

import org.springframework.data.domain.Pageable;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.user.entity.Role;

public interface RoleService {

	/**
	 * 
	* @Description: TODO(根据id获取角色详情) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:07:38
	 */
	public Role getRoleById(String id);

	/**
	 * 
	* @Description: TODO(根据条件获取角色列表) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:16:54
	 */
	public QueryResult<Role> getRolelist(Class<Role> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable);

	/**
	 * 
	* @Description: TODO(保存角色数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:27:02
	 */
	public  void save(Role role);

	/**
	 * 
	* @Description: TODO(更新角色数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:27:15
	 */
	public  void update(Role role);

}

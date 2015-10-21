package com.sdf.manager.user.service.impl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.user.entity.Role;
import com.sdf.manager.user.repository.RoleRepository;
import com.sdf.manager.user.service.RoleService;

@Service("RoleService")
@Transactional(propagation=Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService 
{
	
	@Autowired
	private RoleRepository roleRepository;
	
	/**
	 * 
	* @Description: TODO(根据id获取角色详情) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:07:38
	 */
	public Role getRoleById(String id)
	{
		Role role = roleRepository.getRoleById(id);
		return role;
	}
	
	/**
	 * 
	* @Description: TODO(根据条件获取角色列表) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:16:54
	 */
	public QueryResult<Role> getRolelist(Class<Role> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable)
	{
		
		QueryResult<Role> rolelist = roleRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		return rolelist;
	}
	
	/**
	 * 
	* @Description: TODO(保存角色数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:27:02
	 */
	public void save(Role role)
	{
		roleRepository.save(role);
	}
	
	/**
	 * 
	* @Description: TODO(更新角色数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:27:15
	 */
	public void update(Role role)
	{
		roleRepository.save(role);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

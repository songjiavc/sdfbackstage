package com.sdf.manager.user.repository;

import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.user.entity.Role;

public interface RoleRepository extends GenericRepository<Role,String>
{
	/**
	 * 
	* @Description: TODO(根据id获取角色详情) 
	* @author bann@sdfcp.com
	* @date 2015年10月19日 下午2:04:56
	 */
	@Query("select u from Role u where u.isDeleted ='1' and u.id =?1")
	public Role getRoleById(String id);
	
	/** 
	  * @Description: 根据roleCode获取role实体
	  * @author songj@sdfcp.com
	  * @date 2015年11月25日 上午10:57:51 
	  * @param code
	  * @return 
	  */
	@Query("select u from Role u where u.isDeleted ='1' and u.code =?1")
	public Role getRoleByCode(String code);
}

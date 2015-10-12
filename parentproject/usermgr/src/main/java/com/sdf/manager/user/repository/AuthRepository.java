package com.sdf.manager.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.user.bean.AuthorityBean;
import com.sdf.manager.user.entity.Authority;

/** 
  * @ClassName: AuthRepository 
  * @Description: 权限 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:24:21 
  *  
  */
public interface AuthRepository extends JpaRepository<Authority, String> {
	
	
	/**
	 * 
	* @Description: TODO(根据code获取权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午3:37:34
	 */
	@Query("select u from Authority u where u.code =?1")
	public Authority getAuthorityByCode(String code);
	
	
	/**
	 * 
	* @Description: TODO(根据条件获取list数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月10日 上午10:25:45
	 */
//	public List<Authority> getAuthorityDatas(AuthorityBean authorityBean);
	
	
	
}

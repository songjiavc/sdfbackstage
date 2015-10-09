package com.sdf.manager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sdf.manager.user.entity.Authority;

/** 
  * @ClassName: AuthRepository 
  * @Description: 权限 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:24:21 
  *  
  */
public interface AuthRepository extends JpaRepository<Authority, Integer> {
	
}

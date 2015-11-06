package com.sdf.manager.product.repository;


import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.product.entity.ProductZL;

public interface ProductZLRepository extends GenericRepository<ProductZL, String> {

	/**
	 * 
	* @Description: 根据code查询产品中类信息 
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:00:43
	 */
	@Query("select u from ProductZL u where  u.code =?1")
	public ProductZL getProductZLByCode(String code);
	
}

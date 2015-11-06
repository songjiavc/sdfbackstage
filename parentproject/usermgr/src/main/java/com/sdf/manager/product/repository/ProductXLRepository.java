package com.sdf.manager.product.repository;


import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.product.entity.ProductXL;

public interface ProductXLRepository extends GenericRepository<ProductXL, String> {

	/**
	 * 
	* @Description: 根据code查询产品小类信息 
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:00:43
	 */
	@Query("select u from ProductXL u where  u.code =?1")
	public ProductXL getProductXLByCode(String code);
	
}

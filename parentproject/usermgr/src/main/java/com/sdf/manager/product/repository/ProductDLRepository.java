package com.sdf.manager.product.repository;


import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.product.entity.ProductDL;

public interface ProductDLRepository extends GenericRepository<ProductDL, String> {

	/**
	 * 
	* @Description: 根据code查询产品大类信息 
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:00:43
	 */
	@Query("select u from ProductDL u where  u.code =?1")
	public ProductDL getProductDLByCode(String code);
}

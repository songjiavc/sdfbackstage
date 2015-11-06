package com.sdf.manager.product.repository;

import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.product.entity.Product;

public interface ProductRepository extends GenericRepository<Product, String> {
	
	/**
	 * 
	* @Description: 根据产品的id查询状态为有效的产品数据
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午9:56:39
	 */
	@Query("select u from Product u where  u.isDeleted ='1' and u.id =?1")
	public Product getProductById(String id);
	
}

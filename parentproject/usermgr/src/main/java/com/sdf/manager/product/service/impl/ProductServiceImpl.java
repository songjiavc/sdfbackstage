package com.sdf.manager.product.service.impl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.product.entity.Product;
import com.sdf.manager.product.repository.ProductRepository;
import com.sdf.manager.product.service.ProductService;
import com.sdf.manager.user.entity.Authority;


@Service("productService")
@Transactional(propagation=Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * 
	* @Description: 根据产品id查询状态为有效的产品数据 
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午9:58:02
	 */
	public Product getProductById(String id)
	{
		Product product = productRepository.getProductById(id);
		
		return product;
	}
	
	/**
	 * 
	* @Description:保存产品实体
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午10:02:46
	 */
	public void save(Product product)
	{
		productRepository.save(product);
	}
	
	/**
	 * 
	* @Description: 更改产品实体
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午10:03:22
	 */
	public void update(Product product)
	{
		productRepository.save(product);
	}
	
	/**
	 * 
	* @Description: 根据查询条件分页查询产品数据 
	* @author bann@sdfcp.com
	* @date 2015年11月3日 下午2:03:02
	 */
	public QueryResult<Product> getProductList(Class<Product> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable)
	{
		
		QueryResult<Product> productlist = productRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		return productlist;
	}
	
	
	
	
	
	
}

package com.sdf.manager.product.service;

import java.util.LinkedHashMap;

import org.springframework.data.domain.Pageable;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.product.entity.Product;

public interface ProductService {

	
	public Product getProductById(String id);
	
	
	public void save(Product product);
	
	public void update(Product product);
	
	public QueryResult<Product> getProductList(Class<Product> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
}

package com.sdf.manager.product.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.product.application.dto.ProductDto;
import com.sdf.manager.product.entity.CodeDuration;
import com.sdf.manager.product.entity.Product;

public interface ProductService {

	
	public Product getProductById(String id);
	
	
	public void save(Product product);
	
	public void update(Product product);
	
	public QueryResult<Product> getProductList(Class<Product> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public  ProductDto toDTO(Product entity);
	
	public  List<ProductDto> toDTOS(List<Product> entities);
	
	public List<CodeDuration> getCodeDurationAll();
	
	/**
	 * 
	* @Title: getCodeDurationById
	* @Description: 根据id获取使用期数据
	* @Author : banna
	* @param @param id
	* @param @return    设定文件
	* @return CodeDuration    返回类型
	* @throws
	 */
	public CodeDuration getCodeDurationById(String id);
}

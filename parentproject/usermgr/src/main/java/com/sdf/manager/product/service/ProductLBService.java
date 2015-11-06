package com.sdf.manager.product.service;

import java.util.List;

import com.sdf.manager.product.entity.ProductDL;
import com.sdf.manager.product.entity.ProductXL;
import com.sdf.manager.product.entity.ProductZL;


public interface ProductLBService {

	public ProductDL getProductDLByCode(String code);
	
	public ProductZL getProductZLByCode(String code);
	
	public ProductXL getProductXLByCode(String code);
	
	public List<ProductDL> findAll();
	
	public List<ProductZL> findProductZLs(String dlCode);
	
	public List<ProductXL> findProductXLs(String dlCode,String zlCode);
}

package com.sdf.manager.product.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.product.entity.ProductDL;
import com.sdf.manager.product.entity.ProductXL;
import com.sdf.manager.product.entity.ProductZL;
import com.sdf.manager.product.repository.ProductDLRepository;
import com.sdf.manager.product.repository.ProductXLRepository;
import com.sdf.manager.product.repository.ProductZLRepository;
import com.sdf.manager.product.service.ProductLBService;


@Service("productLBService")
@Transactional(propagation=Propagation.REQUIRED)
public class ProductLBServiceImpl implements ProductLBService {

	@Autowired
	private ProductDLRepository productDLRepository;
	
	@Autowired
	private ProductZLRepository productZLRepository;
	
	@Autowired
	private ProductXLRepository productXLRepository;
	
	/**
	 * 
	* @Description: 根据code查询产品大类信息 
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:00:43
	 */
	public ProductDL getProductDLByCode(String code) {
		return productDLRepository.getProductDLByCode(code);
	}
	
	/**
	 * 
	* @Description: 查询所有的产品大类别
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:15:30
	 */
	public List<ProductDL> findAll()
	{
		List<ProductDL> provinceList = productDLRepository.findAll();
		return provinceList;
	}
	
	/*public List<ProductDL> findCitiesOfProvice() {
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,10000);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		
		QueryResult<ProductDL> cityList = productDLRepository.getScrollDataByJpql
				(ProductDL.class, buffer.toString(), params.toArray(),
						orderBy, pageable);
		
		List<ProductDL> cities = cityList.getResultList();
		
		return cities;
	}*/

	/**
	 * 
	* @Description: 根据code查询产品中类信息 
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:00:43
	 */
	public ProductZL getProductZLByCode(String code) {
		return productZLRepository.getProductZLByCode(code);
	}
	
	/**
	 * 
	* @Description: 根据产品大类查询下属的产品种类数据 
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:17:59
	 */
	public List<ProductZL> findProductZLs(String dlCode) {
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,10000);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		
		if(null != dlCode && !"".equals(dlCode))
		{
			params.add(dlCode);//根据产品大类别code查询数据
			buffer.append(" dlCode = ?").append(params.size());
			
			orderBy.put("code", "asc");
		}
		
		
		QueryResult<ProductZL> pzlList = productZLRepository.getScrollDataByJpql
				(ProductZL.class, buffer.toString(), params.toArray(),
						orderBy, pageable);
		
		List<ProductZL> productZLs = pzlList.getResultList();
		
		return productZLs;
	}

	/**
	 * 
	* @Description: 根据code查询产品小类信息 
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:00:43
	 */
	public ProductXL getProductXLByCode(String code) {
		return productXLRepository.getProductXLByCode(code);
	}
	
	/**
	 * 
	* @Description: 根据产品大类别和产品中类别查询产品小类数据
	* @author bann@sdfcp.com
	* @date 2015年11月5日 下午4:20:04
	 */
	public List<ProductXL> findProductXLs(String dlCode,String zlCode) {
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,10000);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		
		if(null != dlCode && !"".equals(dlCode))
		{
			params.add(dlCode);//根据产品大类别code查询数据
			buffer.append(" dlCode = ?").append(params.size());
			
			orderBy.put("code", "asc");
		}
		
		if(null != zlCode && !"".equals(zlCode))
		{
			params.add(zlCode);//根据产品中类别code查询数据
			buffer.append(" and  zlCode = ?").append(params.size());
			
			orderBy.put("code", "asc");
		}
		
		
		QueryResult<ProductXL> pxlList = productXLRepository.getScrollDataByJpql
				(ProductXL.class, buffer.toString(), params.toArray(),
						orderBy, pageable);
		
		List<ProductXL> productXLs = pxlList.getResultList();
		
		return productXLs;
	}
	
	

}

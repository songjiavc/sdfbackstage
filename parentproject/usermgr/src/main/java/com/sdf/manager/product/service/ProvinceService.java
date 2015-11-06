package com.sdf.manager.product.service;

import java.util.List;

import com.sdf.manager.product.entity.Province;

public interface ProvinceService {

	/**
	 * 
	* @Description: 查询所有的省信息
	* @author bann@sdfcp.com
	* @date 2015年11月4日 上午9:02:38
	 */
	public List<Province> findAll();
	
	/**
	 * 
	* @Description: 根据省份code查询省的信息
	* @author bann@sdfcp.com
	* @date 2015年11月5日 上午9:26:39
	 */
	public Province getProvinceByPcode(String pcode);
}

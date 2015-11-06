package com.sdf.manager.product.service;

import java.util.List;

import com.sdf.manager.product.entity.City;

public interface CityService {

	/**
	 * 
	* @Description: 根据省份id查询市数据 
	* @author bann@sdfcp.com
	* @date 2015年11月4日 上午9:14:52
	 */
	public List<City> findCitiesOfProvice(String provinceId);
	
	/**
	 * 
	* @Description: 根据ccode查询城市信息 
	* @author bann@sdfcp.com
	* @date 2015年11月5日 上午9:31:11
	 */
	public City getCityByCcode(String code);
}

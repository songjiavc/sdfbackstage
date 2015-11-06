package com.sdf.manager.product.repository;


import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.product.entity.City;
import com.sdf.manager.product.entity.Province;

public interface CityRepository extends GenericRepository<City, String> {

	/**
	 * 
	* @Description: 根据ccode查询城市信息
	* @author bann@sdfcp.com
	* @date 2015年11月5日 上午9:32:22
	 */
	@Query("select u from City u where  u.ccode =?1")
	public City getCityByCcode(String ccode);
}

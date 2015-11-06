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
import com.sdf.manager.product.entity.City;
import com.sdf.manager.product.repository.CityRepository;
import com.sdf.manager.product.service.CityService;



@Service("cityService")
@Transactional(propagation=Propagation.REQUIRED)
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;
	
	
	/**
	 * 
	* @Description: 根据省份id查询市数据 
	* @author bann@sdfcp.com
	* @date 2015年11月4日 上午9:14:52
	 */
	public List<City> findCitiesOfProvice(String provinceId) {
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,10000);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		
		if(null != provinceId && !"".equals(provinceId))
		{
			params.add(provinceId);//根据省份查询市数据
			buffer.append(" provinceCode = ?").append(params.size());
			
			orderBy.put("id", "asc");
		}
		
		
		
		
		QueryResult<City> cityList = cityRepository.getScrollDataByJpql
				(City.class, buffer.toString(), params.toArray(),
						orderBy, pageable);
		
		List<City> cities = cityList.getResultList();
		
		return cities;
	}

	public City getCityByCcode(String code) {
		return cityRepository.getCityByCcode(code);
	}

	
	
}

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
import com.sdf.manager.product.entity.Region;
import com.sdf.manager.product.repository.RegionRepository;
import com.sdf.manager.product.service.RegionService;



@Service("regionService")
@Transactional(propagation=Propagation.REQUIRED)
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionRepository regionRepository;
	
	
	/**
	 * 
	* @Description: 根据省份id查询市数据 
	* @author bann@sdfcp.com
	* @date 2015年11月4日 上午9:14:52
	 */
	public List<Region> findRegionsOfCity(String cityId) {
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,10000);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		
		if(null != cityId && !"".equals(cityId))
		{
			params.add(cityId);//根据省份查询市数据
			buffer.append(" cityCode = ?").append(params.size());
			orderBy.put("id", "asc");
		}
		QueryResult<Region> resultList = regionRepository.getScrollDataByJpql
				(Region.class, buffer.toString(), params.toArray(),
						orderBy, pageable);
		List<Region> regions = resultList.getResultList();
		return regions;
	}

	public Region getRegionByAcode(String code) {
		return regionRepository.getRegionByAcode(code);
	}
	
}

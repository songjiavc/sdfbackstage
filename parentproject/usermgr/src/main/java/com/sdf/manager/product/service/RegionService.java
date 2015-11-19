package com.sdf.manager.product.service;

import java.util.List;

import com.sdf.manager.product.entity.Region;

public interface RegionService {
	/** 
	  * @Description: 根据市id查找区、县、镇
	  * @author songj@sdfcp.com
	  * @date 2015年11月16日 上午11:09:30 
	  * @param cityId
	  * @return 
	  */
	public List<Region> findRegionsOfCity(String cityId);
	
	/** 
	  * @Description: 根据code查找
	  * @author songj@sdfcp.com
	  * @date 2015年11月16日 上午11:10:33 
	  * @param code
	  * @return 
	  */
	public Region getRegionByAcode(String code);
}

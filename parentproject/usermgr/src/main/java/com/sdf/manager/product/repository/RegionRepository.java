package com.sdf.manager.product.repository;


import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.product.entity.Region;

public interface RegionRepository extends GenericRepository<Region, String> {

	/** 
	  * @Description: 根据code获取区域实体
	  * @author songj@sdfcp.com
	  * @date 2015年11月16日 上午11:24:44 
	  * @param acode
	  * @return 
	  */
	@Query("select u from Region u where  u.acode =?1")
	public Region getRegionByAcode(String acode);
}

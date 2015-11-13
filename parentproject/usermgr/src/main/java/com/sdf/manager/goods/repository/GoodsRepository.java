package com.sdf.manager.goods.repository;

import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.goods.entity.Goods;

public interface GoodsRepository extends GenericRepository<Goods, String>{

	
	@Query("select u from Goods u where u.isDeleted='1' and u.id =?1")
	public Goods getGoodsById(String id);
	
	@Query("select u from Goods u where u.isDeleted='1' and u.code =?1")
	public Goods getGoodsByCode(String code);
}

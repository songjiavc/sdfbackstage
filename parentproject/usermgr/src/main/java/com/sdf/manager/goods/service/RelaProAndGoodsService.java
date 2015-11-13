package com.sdf.manager.goods.service;

import java.util.List;

import com.sdf.manager.goods.entity.RelaSdfGoodProduct;

public interface RelaProAndGoodsService {

	
	public void save(RelaSdfGoodProduct entity);
	
	/**
	 * 
	* @Description:批量保存产品--商品关联表数据
	* @author bann@sdfcp.com
	* @date 2015年11月13日 上午11:06:38
	 */
	public void saveRelapGoodsList(List<RelaSdfGoodProduct> entities);
	
	/**
	 * 
	* @Description: 批量删除产品--商品关联表数据
	* @author bann@sdfcp.com
	* @date 2015年11月13日 上午11:10:55
	 */
	public void deleteRelapGoodsList(List<RelaSdfGoodProduct> entities);
}

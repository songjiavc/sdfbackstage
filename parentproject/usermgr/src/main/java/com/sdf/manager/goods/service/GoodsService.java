package com.sdf.manager.goods.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.goods.dto.GoodsDTO;
import com.sdf.manager.goods.dto.RelaSdfGoodProductDTO;
import com.sdf.manager.goods.entity.Goods;
import com.sdf.manager.goods.entity.RelaSdfGoodProduct;

public interface GoodsService {

	
	/**
	 * 
	* @Description: 保存商品实体 
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:20:13
	 */
	public void save(Goods goods);
	
	
	/**
	 * 
	* @Description: 更改商品实体 
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:21:15
	 */
	public void update(Goods goods);
	
	/**
	 * 
	* @Description: 根据id查询有效的商品数据
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:24:31
	 */
	public Goods getGoodsById(String id);
	
	/**
	 * 
	* @Description: 根据商品code查询商品数据 
	* @author bann@sdfcp.com
	* @date 2015年11月13日 上午11:44:10
	 */
	public Goods getGoodsByCode(String code);
	
	/**
	 * 
	* @Description:根据查询条件查询商品列表数据 
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:26:14
	 */
	public QueryResult<Goods> getGoodsList(Class<Goods> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public  GoodsDTO toDTO(Goods entity);
	
	public  List<GoodsDTO> toDTOS(List<Goods> entities);
	
	public  RelaSdfGoodProductDTO toRDTO(RelaSdfGoodProduct entity);
	
	public  List<RelaSdfGoodProductDTO> toRDTOS(List<RelaSdfGoodProduct> entities);
	
}

package com.sdf.manager.goods.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.common.util.BeanUtil;
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.common.util.DateUtil;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.goods.dto.GoodsDTO;
import com.sdf.manager.goods.dto.RelaSdfGoodProductDTO;
import com.sdf.manager.goods.entity.Goods;
import com.sdf.manager.goods.entity.RelaSdfGoodProduct;
import com.sdf.manager.goods.repository.GoodsRepository;
import com.sdf.manager.goods.service.GoodsService;
import com.sdf.manager.product.entity.City;
import com.sdf.manager.product.entity.Province;
import com.sdf.manager.product.service.CityService;
import com.sdf.manager.product.service.ProductService;
import com.sdf.manager.product.service.ProvinceService;


@Service("goodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	 private ProvinceService provinceService;
	 
	 @Autowired
	 private CityService cityService;
	 
	 @Autowired
	 private ProductService productService;
	
	/**
	 * 
	* @Description: 保存商品实体 
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:20:13
	 */
	public void save(Goods goods)
	{
		goodsRepository.save(goods);
	}
	
	/**
	 * 
	* @Description: 更改商品实体 
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:21:15
	 */
	public void update(Goods goods)
	{
		goodsRepository.save(goods);
	}
	
	/**
	 * 
	* @Description: 根据id查询有效的商品数据
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:24:31
	 */
	public Goods getGoodsById(String id)
	{
		Goods goods = goodsRepository.getGoodsById(id);
		
		return goods;
	}
	
	/**
	 * 
	* @Description: 根据商品code查询商品数据 
	* @author bann@sdfcp.com
	* @date 2015年11月13日 上午11:44:10
	 */
	public Goods getGoodsByCode(String code)
	{
		Goods goods = goodsRepository.getGoodsByCode(code);
		
		return goods;
	}
	
	
	
	/**
	 * 
	* @Description:根据查询条件查询商品列表数据 
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:26:14
	 */
	public QueryResult<Goods> getGoodsList(Class<Goods> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable)
	{
		
		QueryResult<Goods> goodslist = goodsRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		return goodslist;
	}
	
	
	
	
	 /************Assembler class***************/
	 /**
		 * 
		* @Description: 将产品entity 转换为dto
		* @author bann@sdfcp.com
		* @date 2015年11月5日 上午10:51:38
		 */
	public  GoodsDTO toDTO(Goods entity) {
			GoodsDTO dto = new GoodsDTO();
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				//处理实体中的特殊转换值
				if(null != entity.getCreaterTime())//创建时间
				{
					dto.setCreateTime(DateUtil.formatDate(entity.getCreaterTime(), DateUtil.FULL_DATE_FORMAT));
				}
				if(null != entity.getProvinceDm())//省级区域
				{
					Province province = new Province();
					province = provinceService.getProvinceByPcode(entity.getProvinceDm());
					dto.setProvinceName(null != province?province.getPname():"");
				}
				if(null != entity.getCityDm())//市级区域
				{
					if(Constants.CITY_ALL.equals(entity.getCityDm()))
					{
						dto.setCityName(Constants.CITY_ALL_NAME);
					}
					else
					{
						City city = new City();
						city = cityService.getCityByCcode(entity.getCityDm());
						dto.setCityName(null != city?city.getCname():"");
					}
					
				}
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
						
			return dto;
		}
		
		/**
		 * 
		* @Description:批量将产品实体转换为dto
		* @author bann@sdfcp.com
		* @date 2015年11月5日 上午10:52:35
		 */
		public  List<GoodsDTO> toDTOS(List<Goods> entities) {
			List<GoodsDTO> dtos = new ArrayList<GoodsDTO>();
			GoodsDTO dto;
			for (Goods entity : entities) 
			{
				dto = toDTO(entity);
				dtos.add(dto);
			}
			return dtos;
		}
	 
	 
	 
		public  RelaSdfGoodProductDTO toRDTO(RelaSdfGoodProduct entity) {
			RelaSdfGoodProductDTO dto = new RelaSdfGoodProductDTO();
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				//处理实体中的特殊转换值
				if(null != entity.getGoods())
				{
					dto.setGoodId(entity.getGoods().getId());
				}
				if(null != entity.getProduct())
				{
					dto.setProductId(entity.getProduct().getId());
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
						
			return dto;
		}
	
		public  List<RelaSdfGoodProductDTO> toRDTOS(List<RelaSdfGoodProduct> entities) {
			List<RelaSdfGoodProductDTO> dtos = new ArrayList<RelaSdfGoodProductDTO>();
			RelaSdfGoodProductDTO dto;
			for (RelaSdfGoodProduct entity : entities) 
			{
				dto = toRDTO(entity);
				dtos.add(dto);
			}
			return dtos;
		}
	
	
}

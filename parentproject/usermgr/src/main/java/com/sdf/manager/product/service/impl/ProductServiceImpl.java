package com.sdf.manager.product.service.impl;

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
import com.sdf.manager.goods.entity.RelaSdfGoodProduct;
import com.sdf.manager.product.application.dto.ProductDto;
import com.sdf.manager.product.entity.City;
import com.sdf.manager.product.entity.Product;
import com.sdf.manager.product.entity.ProductDL;
import com.sdf.manager.product.entity.ProductXL;
import com.sdf.manager.product.entity.ProductZL;
import com.sdf.manager.product.entity.Province;
import com.sdf.manager.product.repository.ProductRepository;
import com.sdf.manager.product.service.CityService;
import com.sdf.manager.product.service.ProductLBService;
import com.sdf.manager.product.service.ProductService;
import com.sdf.manager.product.service.ProvinceService;
import com.sdf.manager.user.entity.Authority;


@Service("productService")
@Transactional(propagation=Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	 @Autowired
	 private ProvinceService provinceService;
	 
	 @Autowired
	 private CityService cityService;
	 
	 @Autowired
	 private ProductLBService productLBService;
	
	/**
	 * 
	* @Description: 根据产品id查询状态为有效的产品数据 
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午9:58:02
	 */
	public Product getProductById(String id)
	{
		Product product = productRepository.getProductById(id);
		
		return product;
	}
	
	/**
	 * 
	* @Description:保存产品实体
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午10:02:46
	 */
	public void save(Product product)
	{
		productRepository.save(product);
	}
	
	/**
	 * 
	* @Description: 更改产品实体
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午10:03:22
	 */
	public void update(Product product)
	{
		productRepository.save(product);
	}
	
	/**
	 * 
	* @Description: 根据查询条件分页查询产品数据 
	* @author bann@sdfcp.com
	* @date 2015年11月3日 下午2:03:02
	 */
	public QueryResult<Product> getProductList(Class<Product> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable)
	{
		
		QueryResult<Product> productlist = productRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		return productlist;
	}
	
	
	 /************Assembler class***************/
	 /**
		 * 
		* @Description: 将产品entity 转换为dto
		* @author bann@sdfcp.com
		* @date 2015年11月5日 上午10:51:38
		 */
		public  ProductDto toDTO(Product entity) {
			ProductDto dto = new ProductDto();
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
				if(null != entity.getCpdlDm())//产品大类
				{
					ProductDL productDL = new ProductDL();
					productDL = productLBService.getProductDLByCode(entity.getCpdlDm());
					dto.setCpdlName(null != productDL?productDL.getName():"");
				}
				
				if(null != entity.getCpzlDm())//产品中类
				{
					if(Constants.PRODUCT_ZL_ALL.equals(entity.getCpzlDm()))
					{
						dto.setCpzlName(Constants.PRODUCT_ZL_ALL_NAME);
					}
					else
					{
						ProductZL productZL = new ProductZL();
						productZL = productLBService.getProductZLByCode(entity.getCpzlDm());
						dto.setCpzlName(null != productZL?productZL.getName():"");
					}
					
				}
				
				if(null != entity.getCpxlDm())//产品小类
				{
					if(Constants.PRODUCT_XL_ALL.equals(entity.getCpxlDm()))
					{
						dto.setCpxlName(Constants.PRODUCT_XL_ALL_NAME);
					}
					else
					{
						ProductXL productXL = new ProductXL();
						productXL = productLBService.getProductXLByCode(entity.getCpxlDm());
						dto.setCpxlName(null != productXL?productXL.getName():"");
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
		public  List<ProductDto> toDTOS(List<Product> entities) {
			List<ProductDto> dtos = new ArrayList<ProductDto>();
			ProductDto dto;
			for (Product entity : entities) 
			{
				dto = toDTO(entity);
				dtos.add(dto);
			}
			return dtos;
		}
	 
	
	
	
}

package com.sdf.manager.product.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdf.manager.common.bean.ResultBean;
import com.sdf.manager.common.util.BeanUtil;
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.common.util.DateUtil;
import com.sdf.manager.common.util.LoginUtils;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.goods.entity.Goods;
import com.sdf.manager.goods.entity.RelaSdfGoodProduct;
import com.sdf.manager.goods.service.GoodsService;
import com.sdf.manager.goods.service.RelaProAndGoodsService;
import com.sdf.manager.order.controller.OrderController;
import com.sdf.manager.order.entity.Orders;
import com.sdf.manager.product.application.dto.ProductDto;
import com.sdf.manager.product.entity.City;
import com.sdf.manager.product.entity.Product;
import com.sdf.manager.product.entity.ProductDL;
import com.sdf.manager.product.entity.ProductXL;
import com.sdf.manager.product.entity.ProductZL;
import com.sdf.manager.product.entity.Province;
import com.sdf.manager.product.entity.Region;
import com.sdf.manager.product.service.CityService;
import com.sdf.manager.product.service.ProductLBService;
import com.sdf.manager.product.service.ProductService;
import com.sdf.manager.product.service.ProvinceService;
import com.sdf.manager.product.service.RegionService;


/**
 * 
  * @ClassName: ProductController 
  * @Description: 产品管理的控制层类
  * @author bann@sdfcp.com
  * @date 2015年11月3日 上午9:59:21 
  *
 */
@Controller
@RequestMapping("/product")
public class ProductController 
{
	 @Autowired
	 private ProductService productService;
	 
	 @Autowired
	 private ProvinceService provinceService;
	 
	 @Autowired
	 private CityService cityService;
	 
	 /** 
	  * @author songjia 
	  * @Fields regionService : 
	  */ 
	@Autowired
	 private RegionService regionService;
	 
	 @Autowired
	 private ProductLBService productLBService;
	 
	 @Autowired
	 private RelaProAndGoodsService relaProAndGoodsService;
	 
	 @Autowired
	 private GoodsService goodsService;
	 
	 public static final int SERIAL_NUM_LEN = 6;//订单流水号中自动生成的数字位数
	 
	/**
	 * 
	* @Description: 根据产品id获取产品信息详情
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午9:59:00
	 */
	 @RequestMapping(value = "/getDetailProduct", method = RequestMethod.GET)
	 public @ResponseBody ProductDto getDetailProduct(
			@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	 {
		 Product product = new Product();
		
		 product = productService.getProductById(id);
		 
		 ProductDto productDto = productService.toDTO(product);
	 	
		return productDto;
	 }
	 
	 /**
	  * 
	 * @Description:获取产品数据列表 
	 * @author bann@sdfcp.com
	 * @date 2015年11月3日 下午1:59:02
	  */
	 @RequestMapping(value = "/getProductList", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getProductList(
				@RequestParam(value="page",required=false) int page,
				@RequestParam(value="rows",required=false) int rows,
				@RequestParam(value="province",required=false) String province,//模糊查询所选省
				@RequestParam(value="procode",required=false) String procode,//模糊查询产品编码
				@RequestParam(value="city",required=false) String city,//模糊查询所选市
//				@RequestParam(value="orCity",required=false) String orCity,//查询当前
				@RequestParam(value="productName",required=false) String productName,//模糊查询填写的产品名称
				@RequestParam(value="productDesprition",required=false) String productDesprition,//模糊查询填写的产品描述
				ModelMap model,HttpSession httpSession) throws Exception
		{
			Map<String,Object> returnData = new HashMap<String,Object> ();
			
			//放置分页参数
			Pageable pageable = new PageRequest(page-1,rows);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
			//只查询未删除数据
			params.add("1");//只查询有效的数据
			buffer.append(" isDeleted = ?").append(params.size());
			
			if(null != province && !"".equals(province)&& !Constants.PROVINCE_ALL.equals(province))
			{
				params.add(province);//根据省份查询产品数据
				buffer.append(" and provinceDm = ?").append(params.size());
			}
			
//			if(null != orCity && !"".equals(orCity))
//			{
//				List<String> paraArr = new ArrayList<String> ();
//				paraArr.add(orCity);
//				paraArr.add(Constants.CITY_ALL);
//				params.add(paraArr);
//				// JPQL(javax.persistence.Query)的IN查询参数必须是集合Collection(用List)类型，而HQL还可以是数组类型；
//				buffer.append(" and cityDm in ?").append(params.size());
//				
//			}
			
			if(null != city && !"".equals(city) && !Constants.CITY_ALL.equals(city))
			{
				params.add(city);//根据城市查询产品数据
				buffer.append(" and cityDm = ?").append(params.size());
			}
			
			if(null != productName && !"".equals(productName))
			{
				params.add("%"+productName+"%");//根据产品名称模糊查询产品数据
				buffer.append(" and name like ?").append(params.size());
			}
			
			if(null != procode && !"".equals(procode))
			{
				params.add("%"+procode+"%");//根据产品编码模糊查询产品数据
				buffer.append(" and code like ?").append(params.size());
			}
			
			if(null != productDesprition && !"".equals(productDesprition))
			{
				params.add("%"+productDesprition+"%");//根据产品描述模糊查询产品数据
				buffer.append(" and productDesprition like ?").append(params.size());
			}
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("id", "desc");
			
			QueryResult<Product> productlist = productService.getProductList(Product.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			//处理返回数据(%返回数据的泛型应该是ProductDto，未完成！！%)
			List<Product> products = productlist.getResultList();
			Long totalrow = productlist.getTotalRecord();
			
			//将实体转换为dto
			List<ProductDto> productDtos = productService.toDTOS(products);
			
			returnData.put("rows", productDtos);
			returnData.put("total", totalrow);
			
			return returnData;
		}
	 
	 
	/**
	 * 
	* @Description:保存/修改产品数据
	* @author bann@sdfcp.com
	* @date 2015年11月3日 上午10:04:28
	 */
	 @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
		public @ResponseBody ResultBean saveOrUpdate(
				@RequestParam(value="id",required=false) String id,
				@RequestParam(value="code",required=false) String code,
				@RequestParam(value="name",required=false) String name,
				@RequestParam(value="lotteryType",required=false) String lotteryType,//彩票种类（1：体彩 2：福彩）
				@RequestParam(value="price",required=false) String price,
				@RequestParam(value="privince",required=false) String privince,
				@RequestParam(value="city",required=false) String city,
				@RequestParam(value="cpdl",required=false) String cpdl,
				@RequestParam(value="cpzl",required=false) String cpzl,
				@RequestParam(value="cpxl",required=false) String cpxl,
				@RequestParam(value="productDesprition",required=false) String productDesprition,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		   ResultBean resultBean = new ResultBean ();
		   
		   Product product ;
		   product = productService.getProductById(id);
		   
		   if(null != product)
		   {//产品数据不为空，则进行修改操作
			  
			   product.setName(name);
			   product.setLotteryType(lotteryType);
			   product.setPrice(price);
			   product.setProvinceDm(privince);
			   product.setCityDm(city);
			   product.setCpdlDm(cpdl);
			   product.setCpzlDm(cpzl);
			   product.setCpxlDm(cpxl);
			   product.setProductDesprition(productDesprition);
			   product.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   product.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   productService.update(product);
			   resultBean.setMessage("修改产品信息成功!");
			   resultBean.setStatus("success");
			   
		   }
		   else
		   {
			   product = new Product();
			   product.setCode(code);
			   product.setName(name);
			   product.setLotteryType(lotteryType);
			   product.setPrice(price);
			   product.setProvinceDm(privince);
			   product.setCityDm(city);
			   product.setCpdlDm(cpdl);
			   product.setCpzlDm(cpzl);
			   product.setCpxlDm(cpxl);
			   product.setProductDesprition(productDesprition);
			   product.setCreater(LoginUtils.getAuthenticatedUserCode(httpSession));
			   product.setCreaterTime(new Timestamp(System.currentTimeMillis()));
			   product.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   product.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   product.setIsDeleted("1");//当前数据为有效数据的标记为
			   productService.save(product);
			   
			   resultBean.setMessage("添加产品信息成功!");
			   resultBean.setStatus("success");
		   }
		   
		   
		   
		   
		   return resultBean;
		}
	 
	
	 /**
	  * 
	 * @Description: 删除产品信息（由于产品和商品是关联的，且产品是构成商品的基本元素，所以在删除产品要对商品造成影响
	 * 暂定删除规则为：删除产品同时要删除同时在产品--商品关联表中的相关当前删除产品的数据，并置相关的商品的商品状态为下架） 
	 * @author bann@sdfcp.com
	 * @date 2015年11月3日 上午10:07:51
	  */
	 @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
		public @ResponseBody ResultBean deleteProduct(
				@RequestParam(value="ids",required=false) String[] ids,
				ModelMap model,HttpSession httpSession) throws Exception
		{
			ResultBean resultBean = new ResultBean();
			
			Product product ;
			Goods goods;
			for (String id : ids) 
			{
				product = new Product();
				product =  productService.getProductById(id);
				product.setIsDeleted("0");;//设置当前数据为已删除状态
				product.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				product.setModifyTime(new Timestamp(System.currentTimeMillis()));
				productService.update(product);//保存更改状态的产品实体
				
				//置相关商品状态位为“下架”
				for (RelaSdfGoodProduct relaSdfGoodProduct : product.getGoodAndproduct()) {
					
					goods = relaSdfGoodProduct.getGoods();
					goods.setStatus(Constants.GOODS_OFF_SHELVES);
					goods.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
					goods.setModifyTime(new Timestamp(System.currentTimeMillis()));
					goodsService.update(goods);
				}
				
				//删除产品--商品关联数据
				relaProAndGoodsService.deleteRelapGoodsList(product.getGoodAndproduct());
			}
			
			
			resultBean.setStatus("success");
			resultBean.setMessage("删除成功!");
			
			return resultBean;
		}
	 
	 /**
	  * 
	 * @Description: TODO(获取省列表数据) 
	 * @author bann@sdfcp.com
	 * @date 2015年11月3日 下午4:34:26
	  */
	 @RequestMapping(value = "/getProvinceList", method = RequestMethod.POST)
		public @ResponseBody List<Province> getProvinceList(
				@RequestParam(value="isHasall",required=false) boolean isHasall,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	
		 	List<Province> provinceList = provinceService.findAll();
		 	
		 	//11/4:现阶段业务定义为省份区域为必选，不可选择全部，而市级区域可以选择全部
		 	if(isHasall)
		 	{
		 		Province provineall = new Province();
			 	provineall.setPcode(Constants.PROVINCE_ALL);
			 	provineall.setPname(Constants.PROVINCE_ALL_NAME);	
			 	provinceList.add(provineall);
		 	}
		 	
		 	
		 	return provinceList;
		}
	 
	 /**
	  * 
	 * @Description: TODO(获取省级联的市数据) 
	 * @author bann@sdfcp.com
	 * @date 2015年11月3日 下午4:43:25
	  */
	 @RequestMapping(value = "/getCityList", method = RequestMethod.POST)
		public @ResponseBody List<City> getCityList(
				@RequestParam(value="pcode",required=false) String pcode,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	
		 	List<City> cities = cityService.findCitiesOfProvice(pcode);
		 	
		 	City cityall = new City();
		 	
		 	cityall.setCcode(Constants.CITY_ALL);
		 	cityall.setCname(Constants.CITY_ALL_NAME);
		 	cities.add(cityall);
		 	
		 	return cities;
		}
	
	 /** 
	  * @Description: 获取乡镇代码表
	  * @author songj@sdfcp.com
	  * @date 2015年11月16日 下午1:14:45 
	  * @param pcode
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception 
	  */
	@RequestMapping(value = "/getRegionList", method = RequestMethod.POST)
		public @ResponseBody List<Region> getRegionList(
				@RequestParam(value="ccode",required=false) String ccode,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	List<Region> regions = regionService.findRegionsOfCity(ccode);
		 	Region regionAll = new Region();
		 	regionAll.setAcode(Constants.REGION_ALL);
		 	regionAll.setAname(Constants.REGION_ALL_NAME);
		 	regions.add(regionAll);
		 	return regions;
		}
	 
	 
	 /**
	  * 
	 * @Description: 查询所有的产品大类别数据列表
	 * @author bann@sdfcp.com
	 * @date 2015年11月5日 下午4:51:42
	  */
	 @RequestMapping(value = "/getProductDlList", method = RequestMethod.POST)
		public @ResponseBody List<ProductDL> getProductDlList(
				@RequestParam(value="isHasall",required=false) boolean isHasall,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	
		 	List<ProductDL> productDLList = productLBService.findAll();
		 	
		 	
		 	return productDLList;
		}
	 
	 /**
	  * 
	 * @Description: 根据产品大类别查询产品中类别数据
	 * @author bann@sdfcp.com
	 * @date 2015年11月5日 下午4:53:59
	  */
	 @RequestMapping(value = "/getProductZList", method = RequestMethod.POST)
		public @ResponseBody List<ProductZL> getProductZList(
				@RequestParam(value="dlCode",required=false) String dlCode,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	
		 	List<ProductZL> productZLList = productLBService.findProductZLs(dlCode);
		 	
		 	if(productZLList.size()==0)
		 	{//在中类列表没有数据时，填充“全部”到返回值
		 		ProductZL productZLAll = new ProductZL();
		 		productZLAll.setCode(Constants.PRODUCT_ZL_ALL);
		 		productZLAll.setName(Constants.PRODUCT_ZL_ALL_NAME);
		 		productZLList.add(productZLAll);
		 	}
		 	
		 	return productZLList;
		}
	 
	 /**
	  * 
	 * @Description:根据产品大类别编码或产品中类别编码查询产品小类别数据
	 * @author bann@sdfcp.com
	 * @date 2015年11月5日 下午4:55:09
	  */
	 @RequestMapping(value = "/getProductXList", method = RequestMethod.POST)
		public @ResponseBody List<ProductXL> getProductXList(
				@RequestParam(value="dlCode",required=false) String dlCode,
				@RequestParam(value="zlCode",required=false) String zlCode,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	
		 	List<ProductXL> productXLList = productLBService.findProductXLs(dlCode,zlCode);
		 	
		 	//添加产品小类别的全部选项
		 	ProductXL productXlAll = new ProductXL();
		 	productXlAll.setCode(Constants.PRODUCT_XL_ALL);
		 	productXlAll.setName(Constants.PRODUCT_XL_ALL_NAME);
		 	productXLList.add(productXlAll);
		 	
		 	return productXLList;
		}
	 
	 
	 /**
	  * 
	 * @Description: 校验产品编码唯一性
	 * @author bann@sdfcp.com
	 * @date 2015年11月4日 下午2:58:17
	  */
	 @RequestMapping(value = "/checkCode", method = RequestMethod.POST)
		public @ResponseBody ResultBean  checkCode(
				@RequestParam(value="id",required=false) String id,
				@RequestParam(value="code",required=false) String code,
				ModelMap model,HttpSession httpSession) throws Exception {
			
			ResultBean resultBean = new ResultBean ();
			
			//放置分页参数
			Pageable pageable = new PageRequest(0,10000);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
			//只查询未删除数据
			params.add("1");//只查询有效的数据
			buffer.append(" isDeleted = ?").append(params.size());
			
			if(null != code && !"".equals(code))
			{
				params.add(code);
				buffer.append(" and code = ?").append(params.size());
			}
			
			
			if(null != id && !"".equals(id))
			{//校验修改中的值的唯一性
				params.add(id);
				buffer.append(" and id != ?").append(params.size());
			}
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			
			QueryResult<Product> plist = productService.getProductList(Product.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(plist.getResultList().size()>0)
			{
				resultBean.setExist(true);//若查询的数据条数大于0，则当前输入值已存在，不符合唯一性校验
			}
			else
			{
				resultBean.setExist(false);
			}
			
			return resultBean;
			
		}
	 
	 /**
	  * 
	 * @Description: 校验产品名称唯一性
	 * @author bann@sdfcp.com
	 * @date 2015年11月4日 下午2:58:17
	  */
	 @RequestMapping(value = "/checkProName", method = RequestMethod.POST)
		public @ResponseBody ResultBean  checkProName(
				@RequestParam(value="id",required=false) String id,
				@RequestParam(value="name",required=false) String name,
				ModelMap model,HttpSession httpSession) throws Exception {
			
			ResultBean resultBean = new ResultBean ();
			
			//放置分页参数
			Pageable pageable = new PageRequest(0,10000);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
			//只查询未删除数据
			params.add("1");//只查询有效的数据
			buffer.append(" isDeleted = ?").append(params.size());
			
			if(null != name && !"".equals(name))
			{
				params.add(name);
				buffer.append(" and name = ?").append(params.size());
			}
			
			
			if(null != id && !"".equals(id))
			{//校验修改中的值的唯一性
				params.add(id);
				buffer.append(" and id != ?").append(params.size());
			}
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			
			QueryResult<Product> plist = productService.getProductList(Product.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(plist.getResultList().size()>0)
			{
				resultBean.setExist(true);//若查询的数据条数大于0，则当前输入值已存在，不符合唯一性校验
			}
			else
			{
				resultBean.setExist(false);
			}
			
			return resultBean;
			
		}
	 
	 /**
	  * 
	 * @Description:生成产品编码
	 * @author bann@sdfcp.com
	 * @date 2015年11月30日 上午11:00:57
	  */
	 @RequestMapping(value = "/generateProductcode", method = RequestMethod.POST)
		public @ResponseBody Map<String,Object>  generateProductcode(
				@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception {
		 
		 Map<String,Object> returndata = new HashMap<String, Object>();
		 String code = this.codeGenertor();
		 returndata.put("code", code);
		 returndata.put("operator", LoginUtils.getAuthenticatedUserName(httpSession));
		 
		 return returndata;
				 
	 }
	 
	/**
	 * 
	* @Description:生成产品编码 
	* //规则：年月日(yyyyMMdd)+6位流水号
	* @author bann@sdfcp.com
	* @date 2015年11月18日 上午10:31:02
	 */
	 private  synchronized String codeGenertor()
	 {
		 
		 StringBuffer proCode = new StringBuffer("Pro");
		//获取当前年月日
		 Calendar c =  Calendar.getInstance();
		 
		 int year = c.get(Calendar.YEAR);
		 int month = c.get(Calendar.MONTH)+1;
		 int day = c.get(Calendar.DAY_OF_MONTH);
		 proCode.append(year+"").append(month+"").append(day+"");
		 
		 //验证当天是否已生成产品
		//放置分页参数
			Pageable pageable = new PageRequest(0,10000);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
			params.add(proCode+"%");//根据产品名称模糊查询
			buffer.append(" code like ?").append(params.size());
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("code", "desc");//大号的code排在前面
			
			QueryResult<Product> prolist = productService.getProductList(Product.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(prolist.getResultList().size()>0)
			{
				String maxCode = prolist.getResultList().get(0).getCode();
				String weihao = maxCode.substring(8, maxCode.length());
				int num = Integer.parseInt(weihao);
				String newNum = (++num)+"";
				int needLen = (ProductController.SERIAL_NUM_LEN-newNum.length());
				for(int i=0;i<needLen;i++)
				{
					newNum = "0"+newNum;
				}
				proCode.append(newNum);
			}
			else
			{//当天还没有生成产品编码
				proCode.append("000001");
			}
			
		 
			return proCode.toString();
	 }
	
}

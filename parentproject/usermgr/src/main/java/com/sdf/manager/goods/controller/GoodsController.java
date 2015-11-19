package com.sdf.manager.goods.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdf.manager.common.bean.ResultBean;
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.common.util.LoginUtils;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.goods.dto.GoodsDTO;
import com.sdf.manager.goods.dto.RelaSdfGoodProductDTO;
import com.sdf.manager.goods.entity.Goods;
import com.sdf.manager.goods.entity.RelaSdfGoodProduct;
import com.sdf.manager.goods.service.GoodsService;
import com.sdf.manager.goods.service.RelaProAndGoodsService;
import com.sdf.manager.product.application.dto.ProductDto;
import com.sdf.manager.product.entity.Product;
import com.sdf.manager.product.service.CityService;
import com.sdf.manager.product.service.ProductService;
import com.sdf.manager.product.service.ProvinceService;



/**
 * 
  * @ClassName: GoodsController 
  * @Description: 商品控制层
  * @author bann@sdfcp.com
  * @date 2015年11月9日 下午3:25:35 
  *
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	 private ProvinceService provinceService;
	 
	 @Autowired
	 private CityService cityService;
	 
	 @Autowired
	 private ProductService productService;
	 
	 @Autowired
	 private RelaProAndGoodsService relaProAndGoodsService;
	
	
	/**
	 * 
	* @Description: 根据id查询商品信息的详情
	* @author bann@sdfcp.com
	* @date 2015年11月9日 下午4:29:10
	 */
	 @RequestMapping(value = "/getDetailGoods", method = RequestMethod.GET)
	 public @ResponseBody GoodsDTO getDetailGoods(
			@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	 {
		 Goods goods = new Goods();
		
		 goods = goodsService.getGoodsById(id);
		 
		 GoodsDTO goodsDTO = goodsService.toDTO(goods);
		 
		 return goodsDTO;
	 }
	 
	 /**
	  * 
	 * @Description: 获取当前商品的对应有产品 
	 * @author bann@sdfcp.com
	 * @date 2015年11月12日 下午4:08:55
	  */
	 @RequestMapping(value = "/getProductsOfGoods", method = RequestMethod.GET)
	 public @ResponseBody List<RelaSdfGoodProductDTO> getProductsOfGoods(
			@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	 {
		 Goods goods = new Goods();
		
		 goods = goodsService.getGoodsById(id);
		 
		 
	 	
		 return goodsService.toRDTOS(goods.getGoodAndproduct());
	 }
	 
	 
	 /**
	  * 
	 * @Description: 获取商品的分页数据 
	 * @author bann@sdfcp.com
	 * @date 2015年11月9日 下午4:44:49
	  */
	 @RequestMapping(value = "/getGoodsList", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getGoodsList(
				@RequestParam(value="page",required=false) int page,
				@RequestParam(value="rows",required=false) int rows,
				@RequestParam(value="status",required=false) String status,//商品状态
				@RequestParam(value="province",required=false) String province,//模糊查询所选省
				@RequestParam(value="goodscode",required=false) String goodscode,//模糊查询商品编码
				@RequestParam(value="city",required=false) String city,//模糊查询所选市
				@RequestParam(value="goodsName",required=false) String goodsName,//模糊查询填写的商品名称
				@RequestParam(value="goodsDesprition",required=false) String goodsDesprition,//模糊查询填写的商品描述
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
			
			if(null != city && !"".equals(city) && !Constants.CITY_ALL.equals(city))
			{
				params.add(city);//根据城市查询产品数据
				buffer.append(" and cityDm = ?").append(params.size());
			}
			
			if(null != goodsName && !"".equals(goodsName))
			{
				params.add("%"+goodsName+"%");//根据商品名称模糊查询商品数据
				buffer.append(" and name like ?").append(params.size());
			}
			
			if(null != goodscode && !"".equals(goodscode))
			{
				params.add("%"+goodscode+"%");//根据商品编码模糊查询商品数据
				buffer.append(" and code like ?").append(params.size());
			}
			
			if(null != goodsDesprition && !"".equals(goodsDesprition))
			{
				params.add("%"+goodsDesprition+"%");//根据商品描述模糊查询商品数据
				buffer.append(" and goodsDesprition like ?").append(params.size());
			}
			
			if(null != status && !"".equals(status))
			{
				params.add(status);//根据商品描述模糊查询商品数据
				buffer.append(" and status = ?").append(params.size());
			}
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("id", "desc");
			
			QueryResult<Goods> goodslist = goodsService.getGoodsList(Goods.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			//处理返回数据(%返回数据的泛型应该是ProductDto，未完成！！%)
			List<Goods> goods = goodslist.getResultList();
			Long totalrow = goodslist.getTotalRecord();
			
			//将实体转换为dto
			List<GoodsDTO> goodsDtos = goodsService.toDTOS(goods);
			
			returnData.put("rows", goodsDtos);
			returnData.put("total", totalrow);
			
			return returnData;
		}
	 
	 /**
	  * 
	 * @Description: 获取产品数据列表 
	 * @author bann@sdfcp.com
	 * @date 2015年11月12日 上午11:17:55
	  */
	 @RequestMapping(value = "/getProductList", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getProductList(
				@RequestParam(value="page",required=false) int page,
				@RequestParam(value="rows",required=false) int rows,
				@RequestParam(value="goodsId",required=false) String goodsId,//模糊查询所选省
				@RequestParam(value="province",required=false) String province,//模糊查询所选省
				@RequestParam(value="procode",required=false) String procode,//模糊查询产品编码
				@RequestParam(value="city",required=false) String city,//模糊查询所选市
				@RequestParam(value="orCity",required=false) String orCity,//查询当前
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
			
			if(null != orCity && !"".equals(orCity))
			{
				List<String> paraArr = new ArrayList<String> ();
				paraArr.add(orCity);
				paraArr.add(Constants.CITY_ALL);
				params.add(paraArr);
				// JPQL(javax.persistence.Query)的IN查询参数必须是集合Collection(用List)类型，而HQL还可以是数组类型；
				buffer.append(" and cityDm in ?").append(params.size());
				
			}
			
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
	 * @Description: 保存或修改商品数据
	 * @author bann@sdfcp.com
	 * @date 2015年11月9日 下午4:48:24
	  */
	 @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
		public @ResponseBody ResultBean saveOrUpdate(
				@RequestParam(value="id",required=false) String id,
				@RequestParam(value="code",required=false) String code,
				@RequestParam(value="name",required=false) String name,
				@RequestParam(value="price",required=false) String price,
				@RequestParam(value="status",required=false) String status,
				@RequestParam(value="privince",required=false) String privince,
				@RequestParam(value="city",required=false) String city,
				@RequestParam(value="goodsDesprition",required=false) String goodsDesprition,
				@RequestParam(value="idArr",required=false) String idArr,//选中的产品id数组
				@RequestParam(value="productList",required=false) String productList,//选中的产品数据
				ModelMap model,HttpSession httpSession) throws Exception
		{
		   ResultBean resultBean = new ResultBean ();
		   List<RelaSdfGoodProduct> relas = new ArrayList<RelaSdfGoodProduct>();
		   
		   Goods goods ;
		   goods = goodsService.getGoodsById(id);
		   
		   if(null != goods)
		   {//商品数据不为空，则进行修改操作
			  
			   goods.setName(name);
			   goods.setPrice(price);
			   goods.setProvinceDm(privince);
			   goods.setCityDm(city);
			   goods.setStatus(status);
			   goods.setGoodsDesprition(goodsDesprition);
			   goods.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   goods.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   
			   goodsService.update(goods);
			   
			   
			   JSONObject products = JSONObject.parseObject(productList);
			   List<String> proids =  (List<String>) products.get("keys");
			   Map<String,Object> data =  (Map<String, Object>) products.get("data");
			   String proid="";
			   JSONArray ps;
			   Product p1;
			   for(int i=0;i<proids.size();i++)
			   {
				   RelaSdfGoodProduct r1 = new RelaSdfGoodProduct();
				   proid = proids.get(i);
				   ps = (JSONArray) data.get(proid);
				   r1.setGoods(goods);
				   p1 = productService.getProductById(proid);
				   r1.setProduct(p1);
				   r1.setPrice(ps.getString(0));
				   r1.setProbation(ps.getString(1));
				   relas.add(r1);
			   }
			   //删除之前的商品--产品关联表数据
			   relaProAndGoodsService.deleteRelapGoodsList(goods.getGoodAndproduct());
			   //保存商品--产品关联表数据
			   relaProAndGoodsService.saveRelapGoodsList(relas);
			   
			   resultBean.setMessage("修改商品信息成功!");
			   resultBean.setStatus("success");
			   
		   }
		   else
		   {
			   goods = new Goods();
			   goods.setCode(code);
			   goods.setName(name);
			   goods.setPrice(price);
			   goods.setStatus(status);
			   goods.setProvinceDm(privince);
			   goods.setCityDm(city);
			   goods.setGoodsDesprition(goodsDesprition);
			   goods.setCreater(LoginUtils.getAuthenticatedUserCode(httpSession));
			   goods.setCreaterTime(new Timestamp(System.currentTimeMillis()));
			   goods.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   goods.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   goods.setIsDeleted("1");//当前数据为有效数据的标记为
			   goodsService.save(goods);
			   
			   
			   JSONObject products = JSONObject.parseObject(productList);
			   List<String> proids =  (List<String>) products.get("keys");
			   Map<String,Object> data =  (Map<String, Object>) products.get("data");
			   String proid="";
			   JSONArray ps;
			   Product p1;
			   Goods savegoods = goodsService.getGoodsByCode(code);//因为要根据code获取商品数据，所以商品code要全局唯一
			   for(int i=0;i<proids.size();i++)
			   {
				   RelaSdfGoodProduct r1 = new RelaSdfGoodProduct();
				   proid = proids.get(i);
				   ps = (JSONArray) data.get(proid);
				   r1.setGoods(savegoods);
				   p1 = productService.getProductById(proid);
				   r1.setProduct(p1);
				   r1.setPrice(ps.getString(0));
				   r1.setProbation(ps.getString(1));
				   relas.add(r1);
			   }
			   //保存商品--产品关联表数据
			   relaProAndGoodsService.saveRelapGoodsList(relas);
			   
			   
			   resultBean.setMessage("添加商品信息成功!");
			   resultBean.setStatus("success");
		   }
		   
		   
		   
		   
		   return resultBean;
		}
	 
	
	 /**
	  * 
	 * @Description: 批量操作商品数据状态
	 * @author bann@sdfcp.com
	 * @date 2015年11月9日 上午10:07:51
	  */
	 @RequestMapping(value = "/deleteGoods", method = RequestMethod.POST)
		public @ResponseBody ResultBean deleteGoods(
				@RequestParam(value="ids",required=false) String[] ids,
				@RequestParam(value="operaType",required=false) String operaType,//操作类别（0：删除 1：上架 2：下架）
				ModelMap model,HttpSession httpSession) throws Exception
		{
			ResultBean resultBean = new ResultBean();
			
			if("0".equals(operaType))//操作类别（0：删除 1：上架 2：下架）
			{
				Goods goods ;
				for (String id : ids) 
				{
					goods = new Goods();
					goods =  goodsService.getGoodsById(id);
					goods.setIsDeleted("0");;//设置当前数据为已删除状态
					goods.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
					goods.setModifyTime(new Timestamp(System.currentTimeMillis()));
					goodsService.update(goods);//保存更改状态的商品实体
				}
			}
			else
			{
				Goods goods ;
				String updateStatus = Constants.GOODS_OFF_SHELVES;//默认更新状态为下架
				if("1".equals(operaType))//操作类别（0：删除 1：上架 2：下架）
				{
					updateStatus = Constants.GOODS_SHELVES;
				}
				else
					if("2".equals(operaType))
					{
						updateStatus = Constants.GOODS_OFF_SHELVES;
					}
				for (String id : ids) 
				{
					goods = new Goods();
					goods =  goodsService.getGoodsById(id);
					goods.setStatus(updateStatus);
					goods.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
					goods.setModifyTime(new Timestamp(System.currentTimeMillis()));
					goodsService.update(goods);//保存更改状态的商品实体
				}
			}
			
			
			String returnMsg = "删除成功!";
			if("1".equals(operaType))//操作类别（0：删除 1：上架 2：下架）
			{
				returnMsg = "商品上架成功!";
			}
			else
				if("2".equals(operaType))
				{
					returnMsg =  "商品下架成功!";
				}
			resultBean.setStatus("success");
			resultBean.setMessage(returnMsg);
			
			return resultBean;
		}
	 
	 
	 /**
	  * 
	 * @Description: 校验商品编码唯一性
	 * @author bann@sdfcp.com
	 * @date 2015年11月10日 下午2:10:12
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
			
			QueryResult<Goods> glist = goodsService.getGoodsList(Goods.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(glist.getResultList().size()>0)
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
	 * @Description: 校验商品名称唯一性
	 * @author bann@sdfcp.com
	 * @date 2015年11月10日 下午2:58:17
	  */
	 @RequestMapping(value = "/checkGoodsName", method = RequestMethod.POST)
		public @ResponseBody ResultBean  checkGoodsName(
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
			
			QueryResult<Goods> glist = goodsService.getGoodsList(Goods.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(glist.getResultList().size()>0)
			{
				resultBean.setExist(true);//若查询的数据条数大于0，则当前输入值已存在，不符合唯一性校验
			}
			else
			{
				resultBean.setExist(false);
			}
			
			return resultBean;
			
		}
	 
	 
	
	 
	 
	 
	 
	 
}

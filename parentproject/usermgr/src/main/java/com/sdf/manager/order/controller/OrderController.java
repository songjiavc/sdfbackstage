package com.sdf.manager.order.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.sdf.manager.common.exception.GlobalExceptionHandler;
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.common.util.DateUtil;
import com.sdf.manager.common.util.LoginUtils;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.goods.dto.GoodsDTO;
import com.sdf.manager.goods.entity.Goods;
import com.sdf.manager.goods.entity.RelaSdfGoodProduct;
import com.sdf.manager.goods.service.GoodsService;
import com.sdf.manager.goods.service.RelaProAndGoodsService;
import com.sdf.manager.order.dto.OrdersDTO;
import com.sdf.manager.order.entity.FoundOrderStatus;
import com.sdf.manager.order.entity.OrderNextStatus;
import com.sdf.manager.order.entity.OrderStatus;
import com.sdf.manager.order.entity.Orders;
import com.sdf.manager.order.entity.RelaSdfStationProduct;
import com.sdf.manager.order.service.FoundOrderStatusService;
import com.sdf.manager.order.service.OrderService;
import com.sdf.manager.order.service.OrderStatusService;
import com.sdf.manager.order.service.RelaSdfStationProService;
import com.sdf.manager.product.entity.Product;
import com.sdf.manager.product.service.CityService;
import com.sdf.manager.product.service.ProductService;
import com.sdf.manager.product.service.ProvinceService;
import com.sdf.manager.station.application.dto.StationDto;
import com.sdf.manager.station.entity.Station;
import com.sdf.manager.station.repository.StationRepository;
import com.sdf.manager.station.service.StationService;
import com.sdf.manager.user.entity.Role;
import com.sdf.manager.user.entity.User;
import com.sdf.manager.user.service.RoleService;
import com.sdf.manager.user.service.UserService;


/**
 * 
  * @ClassName: ProductController 
  * @Description: 产品管理的控制层类
  * @author bann@sdfcp.com
  * @date 2015年11月3日 上午9:59:21 
  *
 */
@Controller
@RequestMapping("/order")
public class OrderController extends GlobalExceptionHandler
{
	 private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	 @Autowired
	 private OrderService orderService;
	 
	 @Autowired
	 private ProvinceService provinceService;
	 
	 @Autowired
	 private CityService cityService;
	 
	 @Autowired
	 private GoodsService goodsService;
	 
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private RoleService roleService;
	 
	 @Autowired
	 private FoundOrderStatusService foundOrderStatusService;//状态跟踪表的service层
	 
	 @Autowired
	 private OrderStatusService orderStatusService;//订单状态service层
	 
	 @Autowired
	 private RelaSdfStationProService relaSdfStationProService;
	 
	 @Autowired
	 private StationService stationService;
	 
	 @Autowired
	 private ProductService productService;
	 
	 
	 public static final int SERIAL_NUM_LEN = 6;//订单流水号中自动生成的数字位数
	 
	 public static final String OPERORTYPE_SAVE = "0";//代理编辑页面，保存
	 public static final String OPERORTYPE_SAVEANDCOMMIT = "1";//代理编辑页面，保存并提交
	
	 /***订单状态静态变量 start***/
	 public static final String PROXY_SAVE_ORDER = "01";//代理购买商品形成订单时，代理保存的状态码
	 public static final String ORDER_FINISH = "21";//审批完成且已归档的订单状态
	 public static final String ORDER_STOP = "31";//审批不通过，终止订单审批状态
	 /***订单状态静态变量 end***/
	
	 public static final String DIRECTION_GO = "1";//前进方向标志位
	 public static final String DIRECTION_BACK = "1";//后退方向标志位
	 
	 public static final String PAGE_OPERORTYPE_SAVE = "1";//代理订单列表，提交
	 public static final String PAGE_OPERORTYPE_PASS = "2";//财管订单列表，审批通过
	 public static final String PAGE_OPERORTYPE_REJECT = "3";//财管订单列表，审批驳回
	 public static final String PAGE_OPERORTYPE_STOP = "4";//财管订单列表，不通过
	 
	 public static final String STATION_PRODUCT_INVALID_STATUS = "0";//站点和产品关联数据无效
	 public static final String STATION_PRODUCT_VALID_STATUS = "1";//站点和产品关联数据有效
	 
	 
	 
	 
	/**
	 * 
	* @Description: 根据订单id查询订单数据
	* @author bann@sdfcp.com
	* @date 2015年11月16日 上午9:59:00
	 */
	 @RequestMapping(value = "/getDetailOrders", method = RequestMethod.GET)
	 public @ResponseBody OrdersDTO getDetailOrders(
			@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	 {
		 Orders order = new Orders();
		 
		 order = orderService.getOrdersById(id);
		 
		 OrdersDTO orderDto = orderService.toDTO(order);
	 	 
		 return orderDto;
	 }
	 
	/**
	 * 
	* @Description:获取订单列表数据（※根据当前登录人员的信息加载订单数据，代理是加载其下属站点对应的所有订单的数据）
	* @author bann@sdfcp.com
	* @date 2015年11月16日 下午3:50:33
	 */
	 @RequestMapping(value = "/getOrdersList", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getOrdersList(
				@RequestParam(value="page",required=false) int page,
				@RequestParam(value="rows",required=false) int rows,
				@RequestParam(value="orderName",required=false) String orderName,//模糊查询填写的订单名称
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
			
			if(null != orderName && !"".equals(orderName))
			{
				params.add("%"+orderName+"%");//根据订单名称模糊查询
				buffer.append(" and name like ?").append(params.size());
			}
			
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("id", "desc");
			
			QueryResult<Orders> orderlist = orderService.getOrdersList(Orders.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			
			List<Orders> orders = orderlist.getResultList();
			Long totalrow = orderlist.getTotalRecord();
			
			List<OrdersDTO> orderDtos = orderService.toDTOS(orders);//将实体转换为dto
			
			returnData.put("rows", orderDtos);
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
				@RequestParam(value="price",required=false) String price,
				@RequestParam(value="transCost",required=false) String transCost,//运费
				@RequestParam(value="payMode",required=false) String payMode,//支付方式，下拉框
				@RequestParam(value="receiveAddr",required=false) String receiveAddr,//收件人地址
				@RequestParam(value="receiveTele",required=false) String receiveTele,//收件人电话
				@RequestParam(value="status",required=false) String status,//状态
				@RequestParam(value="goodsList",required=false) String goodsList,//选中的商品数据
				@RequestParam(value="proList",required=false) String proList,//选中的商品下的产品数据和其对应设定的试用期
				@RequestParam(value="station",required=false) String station,//站点
				@RequestParam(value="operatype",required=false) String operatype,//0:保存 1：保存并提交
				ModelMap model,HttpSession httpSession) throws Exception
		{
		   ResultBean resultBean = new ResultBean ();
		   
		   
		   Orders order ;
		   order = orderService.getOrdersById(id);
		   
		   if(null != order)
		   {//商品数据不为空，则进行修改操作
			  
			   order.setName(name);
			   order.setPrice(price);
			   order.setCode(code);
			   order.setTransCost(transCost);
			   order.setPayMode(payMode);
			   order.setStationId(station);
			   order.setReceiveAddr(receiveAddr);
			   order.setReceiveTele(receiveTele);
			   order.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   order.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   
			   JSONArray array = JSONArray.parseArray(goodsList);
			   List<Goods> goods = new ArrayList<Goods>();
			   for (Object goodId : array) {
				   
				   Goods good = goodsService.getGoodsById(goodId.toString());
				   goods.add(good);
				
			   }
			   
			   order.setGoods(goods);
			   String currentStatus = order.getStatus();
			   if(OrderController.OPERORTYPE_SAVE.equals(operatype))
			   {//保存
				   currentStatus = order.getStatus();
			   }
			   else if(OrderController.OPERORTYPE_SAVEANDCOMMIT.equals(operatype))
			   {
				   //根据当前状态获取下一状态
				   OrderNextStatus orderNextStatus = orderStatusService.getOrderNextStatusBycurrentStatusId(order.getStatus(), "1");
				   currentStatus = orderNextStatus.getNextStatusId();//代理保存并提交
				   
			   }
			   order.setStatus(currentStatus);
			   order.setStatusTime(new Timestamp(System.currentTimeMillis()));
			   orderService.update(order);
			   resultBean.setMessage("修改订单信息成功!");
			   resultBean.setStatus("success");
			   
			   if(OrderController.OPERORTYPE_SAVEANDCOMMIT.equals(operatype))
			   {
				 //由于状态变化，将变化状态存入到状态流程跟踪表中
				   this.saveFoundOrderStatus(LoginUtils.getAuthenticatedUserCode(httpSession),currentStatus,order);
			   }
			   
			   /**处理产品和站点关联数据**/
			   //1.删除之前的关联数据
			   List<RelaSdfStationProduct> deleteData = this.getStationAndProducts(id, model, httpSession);
			   for (RelaSdfStationProduct relaSdfStationProduct : deleteData) {
				
				   relaSdfStationProduct.setIsDeleted(Constants.IS_DELETED);
				   relaSdfStationProService.update(relaSdfStationProduct);
			   }
			   //2.保存新关联数据
			   /**处理station<-->product关联关系数据**/
			   JSONObject proOfgoods = JSONObject.parseObject(proList);
			   List<String> proOfgoodsKeys =  (List<String>) proOfgoods.get("keys");
			   Map<String,Object> proOfgoodsData =  (Map<String, Object>) proOfgoods.get("data");
			   List<RelaSdfStationProduct> relaSdfStationProducts = new ArrayList<RelaSdfStationProduct>();
			   for(int m=0;m<proOfgoodsKeys.size();m++)
			   {
				   JSONArray choosegoods = (JSONArray) proOfgoodsData.get(proOfgoodsKeys.get(m));
				   for(int i=0;i<choosegoods.size();i++)
				   {
					   JSONObject products = JSONObject.parseObject(choosegoods.get(i).toString());
					   List<String> proids =  (List<String>) products.get("keys");
					   Map<String,Object> data =  (Map<String, Object>) products.get("data");
					   String proid="";
					   JSONArray ps;
					   Product p1;
					   for(int j=0;j<proids.size();j++)
					   {
						   RelaSdfStationProduct relaSdfStationProduct = new RelaSdfStationProduct();
						   proid = proids.get(j);
						   ps = (JSONArray) data.get(proid);
						   relaSdfStationProduct.setProductId(ps.getString(5));//productId中放置的是产品和商品关联表的id
						   relaSdfStationProduct.setStationId(ps.getString(3));//从前台获取
						   relaSdfStationProduct.setProbation(ps.getString(2));
						   relaSdfStationProduct.setGoodsId(ps.getString(1));
						   relaSdfStationProduct.setOrderId(id);
						   relaSdfStationProduct.setIsDeleted(Constants.IS_NOT_DELETED);
						   relaSdfStationProduct.setCreater(LoginUtils.getAuthenticatedUserCode(httpSession));
						   relaSdfStationProduct.setCreaterTime(new Timestamp(System.currentTimeMillis()));
						   relaSdfStationProduct.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
						   relaSdfStationProduct.setModifyTime(new Timestamp(System.currentTimeMillis()));
						   relaSdfStationProduct.setType(ps.getString(4));
						   relaSdfStationProduct.setStatus(OrderController.STATION_PRODUCT_INVALID_STATUS);//订单开始时无效
						   //计算产品使用期和试用期的和,并放置到使用期的值中
						   relaSdfStationProduct.setDurationOfUse(this.calculateDuration(proid,ps.getString(2)));
						   
						   
						   relaSdfStationProducts.add(relaSdfStationProduct);
						   
						   relaSdfStationProService.save(relaSdfStationProduct);
					   }
				   }
			   }
			   
			   //日志输出
			   logger.info("修改订单--订单code="+code+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			   
		   }
		   else
		   {
			   order = new Orders();
			   order.setName(name);
			   order.setPrice(price);
			   order.setCode(code);
			   order.setTransCost(transCost);
			   order.setPayMode(payMode);
			   order.setReceiveAddr(receiveAddr);
			   order.setReceiveTele(receiveTele);
			   order.setCreater(LoginUtils.getAuthenticatedUserCode(httpSession));
			   order.setStationId(station);
			   //setCreator中放置的是创建订单人的name
			   order.setCreator(LoginUtils.getAuthenticatedUserName(httpSession));
			   order.setCreaterTime(new Timestamp(System.currentTimeMillis()));
			   order.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   order.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   order.setIsDeleted("1");//有效数据
			   String currentStatus = "01";
			   if(OrderController.OPERORTYPE_SAVE.equals(operatype))
			   {
				   currentStatus = OrderController.PROXY_SAVE_ORDER;//购买商品形成订单时，代理保存的状态
			   }
			   else if(OrderController.OPERORTYPE_SAVEANDCOMMIT.equals(operatype))
			   {
				   OrderNextStatus orderNextStatus = orderStatusService.
						   getOrderNextStatusBycurrentStatusId(OrderController.PROXY_SAVE_ORDER,OrderController.DIRECTION_GO );
				   currentStatus = orderNextStatus.getNextStatusId();//代理保存并提交
			   }
			   order.setStatus(currentStatus);//代理保存订单
			   order.setStatusTime(new Timestamp(System.currentTimeMillis()));
			   JSONArray array = JSONArray.parseArray(goodsList);
			   List<Goods> goods = new ArrayList<Goods>();
			   for (Object goodId : array) {
				   
				   Goods good = goodsService.getGoodsById(goodId.toString());
				   goods.add(good);
				
			   }
			   
			   order.setGoods(goods);
			   orderService.save(order);
			   
			   
			   //跟踪订单状态，添加订单状态到订单状态跟踪表中
			   
			   /*finishSaveOrder用处：用来做订单跟踪表与订单表的数据关联，因为若不获取，
			   	则当前操作的订单还不存在，无法存入其状态(订单编码也是全局唯一的！！！)*/
			   Orders finishSaveOrder = orderService.getOrdersByCode(code);
			   
			   if(OrderController.OPERORTYPE_SAVE.equals(operatype))
			   {
				   currentStatus = OrderController.PROXY_SAVE_ORDER;//购买商品形成订单时，代理保存的状态
				   this.saveFoundOrderStatus(LoginUtils.getAuthenticatedUserCode(httpSession),currentStatus,finishSaveOrder);
			   }
			   else if(OrderController.OPERORTYPE_SAVEANDCOMMIT.equals(operatype))
			   {  /*若代理在购买商品时保存并提交到财务管理员审批时，当前订单是有两个状态变化的，
				   所以要向订单状态跟踪表中放入两条数据*/
				  //1.保存“代理保存订单”状态跟踪数据
				   this.saveFoundOrderStatus(LoginUtils.getAuthenticatedUserCode(httpSession),OrderController.PROXY_SAVE_ORDER,order);
				   
				   //2.保存“提交财务管理员”状态跟踪数据
				   this.saveFoundOrderStatus(LoginUtils.getAuthenticatedUserCode(httpSession),currentStatus,finishSaveOrder);
			   }
			   
			   /**处理station<-->product关联关系数据**/
			   JSONObject proOfgoods = JSONObject.parseObject(proList);
			   List<String> proOfgoodsKeys =  (List<String>) proOfgoods.get("keys");
			   Map<String,Object> proOfgoodsData =  (Map<String, Object>) proOfgoods.get("data");
			   List<RelaSdfStationProduct> relaSdfStationProducts = new ArrayList<RelaSdfStationProduct>();
			   for(int m=0;m<proOfgoodsKeys.size();m++)
			   {
				   JSONArray choosegoods = (JSONArray) proOfgoodsData.get(proOfgoodsKeys.get(m));
				   for(int i=0;i<choosegoods.size();i++)//循环选中商品
				   {
					   JSONObject products = JSONObject.parseObject(choosegoods.get(i).toString());//选中的产品id
					   List<String> proids =  (List<String>) products.get("keys");
					   Map<String,Object> data =  (Map<String, Object>) products.get("data");
					   String proid="";
					   JSONArray ps;
					   Product p1;
					   for(int j=0;j<proids.size();j++)
					   {
						   RelaSdfStationProduct relaSdfStationProduct = new RelaSdfStationProduct();
						   proid = proids.get(j);//产品id
						   ps = (JSONArray) data.get(proid);
						   relaSdfStationProduct.setProductId(ps.getString(5));//productId中放置的是产品和商品关联表的id
						   relaSdfStationProduct.setStationId(ps.getString(3));//从前台获取
						   relaSdfStationProduct.setProbation(ps.getString(2));
						   relaSdfStationProduct.setGoodsId(ps.getString(1));
						   relaSdfStationProduct.setOrderId(finishSaveOrder.getId());
						   relaSdfStationProduct.setIsDeleted(Constants.IS_NOT_DELETED);
						   relaSdfStationProduct.setCreater(LoginUtils.getAuthenticatedUserCode(httpSession));
						   relaSdfStationProduct.setCreaterTime(new Timestamp(System.currentTimeMillis()));
						   relaSdfStationProduct.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
						   relaSdfStationProduct.setModifyTime(new Timestamp(System.currentTimeMillis()));
						   relaSdfStationProduct.setType(ps.getString(4));
						   relaSdfStationProduct.setStatus(OrderController.STATION_PRODUCT_INVALID_STATUS);//订单开始时无效
						   //计算产品使用期和试用期的和,并放置到使用期的值中
						   relaSdfStationProduct.setDurationOfUse(this.calculateDuration(proid,ps.getString(2)));
						   
						   relaSdfStationProducts.add(relaSdfStationProduct);
						   
						   relaSdfStationProService.save(relaSdfStationProduct);
					   }
				   }
			   }
			   
			  
			   
			   resultBean.setMessage("添加订单信息成功!");
			   resultBean.setStatus("success");
			   
			   //日志输出
			   logger.info("添加订单--订单code="+code+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			   
			   
		   }
		   
		   
		   
		   
		   return resultBean;
		}
	 
	 /**
	  * 
	 * @Title: calculateDuration
	 * @Description: TODO(计算使用期和试用期的和)
	 * @Author : banna
	 * @param productId:放置的是产品id
	 * @param probation：站点对应该产品的试用期
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	  */
	 private String calculateDuration(String productId,String probation)
	 {
		 
		 Product product = productService.getProductById(productId);
		 
		 String durationOfUser = product.getDurationOfusers().getNumOfDays();
		 
		 int duration = Integer.parseInt(durationOfUser);
		 int probationDay = Integer.parseInt(probation);
		 
		 int count  = duration + probationDay;
		 
		 String countResult = count+"";
		 
		 return countResult;
	 }
	 
	 /**
	  * 
	 * @Description: 保存订单状态跟踪表数据
	 * @author bann@sdfcp.com
	 * @date 2015年11月19日 下午1:45:17
	  */
	 private void saveFoundOrderStatus(String creator,String currentStatus,Orders order)
	 {
		 FoundOrderStatus foundOrderStatus = new FoundOrderStatus();
	     foundOrderStatus.setCreator(creator);
	     foundOrderStatus.setStatus(currentStatus);
	     foundOrderStatus.setStatusSj(new Timestamp(System.currentTimeMillis()));
	     foundOrderStatus.setOrders(order);
	     foundOrderStatusService.save(foundOrderStatus);
	 }
	 
	 /**
	  * 
	 * @Description: 校验订单是否已审批完成并已归档 
	 * @author bann@sdfcp.com
	 * @date 2015年11月19日 下午2:36:41
	  */
	 @RequestMapping(value = "/checkOrderStatus", method = RequestMethod.POST)
		public @ResponseBody ResultBean checkOrderStatus(
				@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	ResultBean resultBean = new ResultBean();
		 	boolean orderFinish = false;
		 	
		 	Orders order = orderService.getOrdersById(id);
		 	
		 	if(OrderController.ORDER_FINISH.equals(order.getStatus()))
		 	{
		 		orderFinish = true;
		 	}
		 	resultBean.setExist(orderFinish);
		 	return resultBean;
		}
	 
	 /**
	  * 
	 * @Description: 获取当前订单的商品数据 
	 * @author bann@sdfcp.com
	 * @date 2015年11月19日 下午2:55:36
	  */
	 @RequestMapping(value = "/getGoodsOfOrder", method = RequestMethod.GET)
		public @ResponseBody List<GoodsDTO> getGoodsOfOrder(
				@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	List<Goods> goods = new ArrayList<Goods>();
		 	
		 	Orders order = orderService.getOrdersById(id);
		 	
		 	if(null != order.getGoods() && order.getGoods().size()>0)
		 	{
		 		goods = order.getGoods();
		 	}
		 	
		 	
		 	List<GoodsDTO> goodsDtos = goodsService.toDTOS(goods);
		 	
		 	return goodsDtos;
		}
	 
	 /**
	  * 
	 * @Description: 根据订单id获取订单下选中的商品下产品和站点关联的数据
	 * @author bann@sdfcp.com
	 * @date 2015年11月26日 下午5:37:11
	  */
	 @RequestMapping(value = "/getStationAndProducts", method = RequestMethod.POST)
		public @ResponseBody List<RelaSdfStationProduct> getStationAndProducts(
				@RequestParam(value="orderId",required=false) String orderId,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	List<RelaSdfStationProduct> relaSdfStationProducts = relaSdfStationProService.getRelaSdfStationProductByOrderId(orderId);
		
		 	return relaSdfStationProducts;
		}
	 
	 
	
	 /**
	  * 
	 * @Description: 删除订单信息
	 * @author bann@sdfcp.com
	 * @date 2015年11月16日 上午10:07:51
	  */
	 @RequestMapping(value = "/deleteOrders", method = RequestMethod.POST)
		public @ResponseBody ResultBean deleteOrders(
				@RequestParam(value="ids",required=false) String[] ids,
				ModelMap model,HttpSession httpSession) throws Exception
		{
			ResultBean resultBean = new ResultBean();
			
			Orders orders;
			List<Goods> goods = new ArrayList<Goods>();
			for (String id : ids) 
			{
				orders = new Orders();
				orders =  orderService.getOrdersById(id);
				orders.setIsDeleted("0");;//设置当前数据为已删除状态
				orders.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				orders.setModifyTime(new Timestamp(System.currentTimeMillis()));
				orders.setGoods(goods);//用来清空订单与商品的关联数据，方便删除商品时判断是否与有效订单关联
				orderService.update(orders);
				
				 //日志输出
				 logger.info("删除订单--订单id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			}
			
			
			resultBean.setStatus("success");
			resultBean.setMessage("删除成功!");
			
			return resultBean;
		}
	 
	 
	 /**
	  * 
	 * @Description: 更新订单状态
	 * @author bann@sdfcp.com
	 * @date 2015年11月23日 下午2:48:16
	  */
	 @RequestMapping(value = "/approveOrders", method = RequestMethod.POST)
		public @ResponseBody ResultBean approveOrders(
				@RequestParam(value="orderId",required=false) String orderId,
				@RequestParam(value="operortype",required=false) String operortype,
				ModelMap model,HttpSession httpSession) throws Exception
		{
			ResultBean resultBean = new ResultBean();
			
			Orders order = orderService.getOrdersById(orderId);
		    String currentStatus = order.getStatus();
		    String directFlag = "1";
		    if(OrderController.PAGE_OPERORTYPE_SAVE.equals(operortype))
		    {//代理审批通过
		    	OrderNextStatus orderNextStatus = orderStatusService.getOrderNextStatusBycurrentStatusId(currentStatus, directFlag);
		    	currentStatus = orderNextStatus.getNextStatusId();
		    }
		    else if(OrderController.PAGE_OPERORTYPE_PASS.equals(operortype))
		    {//财管订单列表审批通过
			   OrderNextStatus orderNextStatus = orderStatusService.getOrderNextStatusBycurrentStatusId(currentStatus,directFlag);
			   currentStatus = orderNextStatus.getNextStatusId();
			   //审批完成后要置“站点<-->产品”关联数据为有效,，即购买此订单产品的站点可以开始使用或试用了，且要初始化当前站点对于产品的使用期的开始时间和结束时间
			   List<RelaSdfStationProduct> relaSdfStationProducts 
			   				= relaSdfStationProService.getRelaSdfStationProductByOrderId(orderId);
			   Date endtime;
			   for (RelaSdfStationProduct relaSdfStationProduct : relaSdfStationProducts) {
				   relaSdfStationProduct.setStatus(OrderController.STATION_PRODUCT_VALID_STATUS);
				   relaSdfStationProduct.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				   relaSdfStationProduct.setModifyTime(new Timestamp(System.currentTimeMillis()));
				   //因为审批已完成，计算当前站点使用该产品的起始时间和终止时间
				 //2016-2-22添加，若当前站点之前购买过当前产品，则开始时间要连续
				   List<RelaSdfStationProduct> saveBefore = relaSdfStationProService.
						   getRelaSdfStationProductByStationIdAndProductIdAndType
						   (relaSdfStationProduct.getStationId(), relaSdfStationProduct.getProductId(), OrderController.STATION_PRODUCT_VALID_STATUS);
				   if(null != saveBefore &&saveBefore.size()>0)
				   {
					   //获取最近一次的购买记录
					   RelaSdfStationProduct nearBuy = saveBefore.get(0);
					   if(null != nearBuy)
					   {
						   Timestamp lastEndtime = nearBuy.getEndTime();//上次一购买应用的到期时间
						   Date newStartTime = DateUtil.
								   				getNextDayOfCurrentTime(lastEndtime, 1);//新的开始时间是从上次购买的到期时间的第二天开始计算的
						   Timestamp newStTimestamp = DateUtil.formatDateToTimestamp(newStartTime, DateUtil.FULL_DATE_FORMAT);//新数据的开始时间
						   
						   Date newendtime;//新数据的结束时间
						   newendtime = DateUtil.getNextDayOfCurrentTime(newStTimestamp, Integer.parseInt(relaSdfStationProduct.getDurationOfUse()));
						   
						   //插入连续的开始时间和结束时间
						   relaSdfStationProduct.setStartTime(newStTimestamp);//开始时间
						   relaSdfStationProduct.setEndTime(DateUtil.formatDateToTimestamp(newendtime, DateUtil.FULL_DATE_FORMAT));
					   }
					   else
					   {
						   relaSdfStationProduct.setStartTime(new Timestamp(System.currentTimeMillis()));//开始时间
						   endtime = DateUtil.getNextDay(Integer.parseInt(relaSdfStationProduct.getDurationOfUse()));
						   relaSdfStationProduct.setEndTime(DateUtil.formatDateToTimestamp(endtime, DateUtil.FULL_DATE_FORMAT));
					   }
				   }
				   else
				   {
					   relaSdfStationProduct.setStartTime(new Timestamp(System.currentTimeMillis()));//开始时间
					   endtime = DateUtil.getNextDay(Integer.parseInt(relaSdfStationProduct.getDurationOfUse()));
					   relaSdfStationProduct.setEndTime(DateUtil.formatDateToTimestamp(endtime, DateUtil.FULL_DATE_FORMAT));
				   }
				   
				  
				   
				   relaSdfStationProService.update(relaSdfStationProduct);
			   }
		    }
		    else if(OrderController.PAGE_OPERORTYPE_REJECT.equals(operortype))
		    {//财管订单列表审批驳回
		       directFlag = "0";
			   OrderNextStatus orderNextStatus = orderStatusService.getOrderNextStatusBycurrentStatusId(currentStatus,directFlag);
			   currentStatus = orderNextStatus.getNextStatusId();
		    }
		    else if(OrderController.PAGE_OPERORTYPE_STOP.equals(operortype))
		    {//财管订单列表不通过
			   currentStatus = OrderController.ORDER_STOP;//置订单状态为“不通过”的状态码
		    }
		    
		   order.setStatus(currentStatus);
		   order.setStatusTime(new Timestamp(System.currentTimeMillis()));
		   order.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
		   order.setModifyTime(new Timestamp(System.currentTimeMillis()));
		   orderService.update(order);
		   
		   //由于状态变化，将变化状态存入到状态流程跟踪表中
			this.saveFoundOrderStatus(LoginUtils.getAuthenticatedUserCode(httpSession),currentStatus,order);
			
			resultBean.setStatus("success");
			resultBean.setMessage("审批订单成功!");
			
			return resultBean;
		}
	 
	 /**
	  * 
	 * @Description: 生成订单code(全局唯一)（日期+流水号）
	 * @author bann@sdfcp.com
	 * @date 2015年11月17日 下午4:09:05
	  */
	 @RequestMapping(value = "/generateOrdercode", method = RequestMethod.POST)
		public @ResponseBody Map<String,Object>  generateOrdercode(
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
	* @Description:生成订单编码 
	* //规则：年月日(yyyyMMdd)+6位流水号
	* @author bann@sdfcp.com
	* @date 2015年11月18日 上午10:31:02
	 */
	 private  synchronized String codeGenertor()
	 {
		 
		 StringBuffer orderCode = new StringBuffer();
		//获取当前年月日
		 String date = "";
		 Date dd  = Calendar.getInstance().getTime();
		 date = DateUtil.formatDate(dd, DateUtil.FULL_DATE_FORMAT);
		 String year = date.substring(0, 4);//半包，不包括最大位数值
		 String month = date.substring(5, 7);
		 String day = date.substring(8, 10);
		 orderCode.append(year).append(month).append(day);
		 
		 //验证当天是否已生成订单
		//放置分页参数
			Pageable pageable = new PageRequest(0,10000);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
/*			//只查询未删除数据
			params.add("1");//只查询有效的数据
			buffer.append(" isDeleted = ?").append(params.size());
*/			
			params.add(orderCode+"%");//根据订单名称模糊查询
			buffer.append(" code like ?").append(params.size());
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("code", "desc");//大号的code排在前面
			
			QueryResult<Orders> orderlist = orderService.getOrdersList(Orders.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(orderlist.getResultList().size()>0)
			{
				String maxCode = orderlist.getResultList().get(0).getCode();
				String weihao = maxCode.substring(8, maxCode.length());
				int num = Integer.parseInt(weihao);
				String newNum = (++num)+"";
				int needLen = (OrderController.SERIAL_NUM_LEN-newNum.length());
				for(int i=0;i<needLen;i++)
				{
					newNum = "0"+newNum;
				}
				orderCode.append(newNum);
			}
			else
			{//当天还没有生成订单号
				orderCode.append("000001");
			}
			
		 
			return orderCode.toString();
	 }
	 
	 /**
	  * 
	 * @Description: 校验订单名称唯一性
	 * @author bann@sdfcp.com
	 * @date 2015年11月16日 下午2:58:17
	  */
	 @RequestMapping(value = "/checkOrderName", method = RequestMethod.POST)
		public @ResponseBody ResultBean  checkOrderName(
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
			
			QueryResult<Orders> orderlist = orderService.getOrdersList(Orders.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(orderlist.getResultList().size()>0)
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
	 * @Description: 获取当前登录用户的角色
	 * @author bann@sdfcp.com
	 * @date 2015年11月24日 下午2:44:42
	  */
	 @RequestMapping(value = "/getLoginuserRole", method = RequestMethod.POST)
		public @ResponseBody ResultBean getLoginuserRole(
				@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	ResultBean resultBean = new ResultBean();
		 	
		 	//获取session中的登录数据
			String code = LoginUtils.getAuthenticatedUserCode(httpSession);
			User user = userService.getUserByCode(code);
			//获取当前登录人员的角色list
			List<Role> roles = user.getRoles();
			
			//根据“代理”和“财政管理员”的角色id获取对应的角色数据，用来判断当前用户的角色中是否有权限
			Role roleProxy = roleService.getRoleById(Constants.ROLE_PROXY_ID);
			Role roleFManger = roleService.getRoleById(Constants.ROLE_FINANCIAL_MANAGER_ID);
			
			if(roles.contains(roleProxy))
			{
				resultBean.setProxy(true);
				//若为代理，返回当前登陆人的id
				resultBean.setMessage(code);
			}
			else
			{
				resultBean.setProxy(false);
			}
			
			if(roles.contains(roleFManger))
			{
				resultBean.setFinancialManager(true);
				resultBean.setMessage(code);
			}
			else
			{
				resultBean.setFinancialManager(false);
			}
			
		 	
		 	return resultBean;
		}
	
	 /**
	  * 
	 * @Description: TODO(获取站点列表数据) 
	 * @author bann@sdfcp.com
	 * @date 2015年11月25日 下午2:39:11
	  */
	 @RequestMapping(value = "/getStationList", method = RequestMethod.POST)
		public @ResponseBody List<StationDto> getStationList(
				@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	
		 	List<Station> stations = new ArrayList<Station>();
		 	//获取当前登录人员的用户信息
			String code = LoginUtils.getAuthenticatedUserCode(httpSession);//登录用户的code
		 	String userId = LoginUtils.getAuthenticatedUserId(httpSession);
			/***根据登录人员的和站点关联的字段，查询当前登录用户的下属站点列表***/
			List<Station> stations2 = new ArrayList<Station>();
			stations2 = stationService.getStationByAgentId(userId);
			List<StationDto> stationDtos = new ArrayList<StationDto>();
			String stationtypeText = "";
			
			for (Station station : stations2) {
				
				if(station.getStationType().equals(Constants.LOTTERY_TYPE_FC))
			 	{
					stationtypeText = "福彩";
			 	}
			 	else
			 		if(station.getStationType().equals(Constants.LOTTERY_TYPE_TC))
				 	{
			 			stationtypeText = "体彩";
				 	}
				
				StationDto stationDto = new StationDto();
				stationDto.setId(station.getId());
				stationDto.setStationNumber(stationtypeText+"--"+station.getStationNumber());
				
				stationDtos.add(stationDto);
			}
		 	
			
		 	return stationDtos;
		}
	 
	 
	 /**
	  * 
	 * @Description: 获取当前站点的其他类别的站点
	 * @author bann@sdfcp.com
	 * @date 2015年12月1日 下午1:58:41
	  */
	 @RequestMapping(value = "/getOtherStations", method = RequestMethod.POST)
		public @ResponseBody List<StationDto> getOtherStations(
				@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	List<StationDto> stationDtos = new ArrayList<StationDto>();
		 	
		 	//1.获取当前选中站点详情
		 	Station station = stationService.getSationById(id);
		 	
		 	String stationType = station.getStationType();
		 	String owner  = station.getOwner();//获取站主姓名
		 	String telephone = station.getOwnerTelephone();//获取站主联系电话
		 	String stationtypeText = "";
		 	
		 	//若是福彩类型，则查询体彩站点，若为体彩类型，则查询福彩站点
		 	if(stationType.equals(Constants.LOTTERY_TYPE_FC))
		 	{
		 		stationType = Constants.LOTTERY_TYPE_TC;
		 		stationtypeText = "体彩";
		 	}
		 	else
		 		if(stationType.equals(Constants.LOTTERY_TYPE_TC))
			 	{
			 		stationType = Constants.LOTTERY_TYPE_FC;
			 		stationtypeText = "福彩";
			 	}
		 	
		 	//2.根据站主姓名和联系电话获取当前站主其他类别站点（规定：站主姓名和站主联系电话可以唯一确定一个站主）
		 	
		 	List<Station> stations = stationService.getStationByStationTypeAndOwnerAndOwnertelephone
		 			(stationType, owner, telephone);
		 	
		 	for (Station station2 : stations) {
				
				StationDto stationDto = new StationDto();
				stationDto.setId(station2.getId());
				stationDto.setStationNumber(stationtypeText+"--"+station2.getStationNumber());
				
				stationDtos.add(stationDto);
			}
		 	
		 	
		 	return stationDtos;
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}

package com.sdf.manager.order.controller;

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
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.sdf.manager.common.bean.ResultBean;
import com.sdf.manager.common.util.LoginUtils;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.goods.entity.Goods;
import com.sdf.manager.goods.service.GoodsService;
import com.sdf.manager.order.dto.OrdersDTO;
import com.sdf.manager.order.entity.Orders;
import com.sdf.manager.order.service.OrderService;
import com.sdf.manager.product.service.CityService;
import com.sdf.manager.product.service.ProvinceService;


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
public class OrderController 
{
	 @Autowired
	 private OrderService orderService;
	 
	 @Autowired
	 private ProvinceService provinceService;
	 
	 @Autowired
	 private CityService cityService;
	 
	 @Autowired
	 private GoodsService goodsService;
	 
	 public static final int SERIAL_NUM_LEN = 6;//订单流水号中自动生成的数字位数
	 
	/**
	 * 
	* @Description: 根据订单id查询订单数据
	* @author bann@sdfcp.com
	* @date 2015年11月16日 上午9:59:00
	 */
	 @RequestMapping(value = "/getDetailOrders", method = RequestMethod.GET)
	 public @ResponseBody Orders getDetailOrders(
			@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	 {
		 Orders order = new Orders();
		
		 order = orderService.getOrdersById(id);
	 	
		 return order;
	 }
	 
	/**
	 * 
	* @Description:获取订单列表数据
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
			   
			   orderService.update(order);
			   resultBean.setMessage("修改订单信息成功!");
			   resultBean.setStatus("success");
			   
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
			   //setCreator中放置的是创建订单人的name
			   order.setCreator(LoginUtils.getAuthenticatedUserName(httpSession));
			   order.setCreaterTime(new Timestamp(System.currentTimeMillis()));
			   order.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   order.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   order.setIsDeleted("1");//有效数据
			   
			   JSONArray array = JSONArray.parseArray(goodsList);
			   List<Goods> goods = new ArrayList<Goods>();
			   for (Object goodId : array) {
				   
				   Goods good = goodsService.getGoodsById(goodId.toString());
				   goods.add(good);
				
			   }
			   
			   order.setGoods(goods);
			   
			   orderService.save(order);
			   resultBean.setMessage("添加订单信息成功!");
			   resultBean.setStatus("success");
			   
			   
		   }
		   
		   
		   
		   
		   return resultBean;
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
			for (String id : ids) 
			{
				orders = new Orders();
				orders =  orderService.getOrdersById(id);
				orders.setIsDeleted("0");;//设置当前数据为已删除状态
				orders.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				orders.setModifyTime(new Timestamp(System.currentTimeMillis()));
				orderService.update(orders);
				
				
			}
			
			
			resultBean.setStatus("success");
			resultBean.setMessage("删除成功!");
			
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
		 Calendar c =  Calendar.getInstance();
		 
		 int year = c.get(Calendar.YEAR);
		 int month = c.get(Calendar.MONTH)+1;
		 int day = c.get(Calendar.DAY_OF_MONTH);
		 orderCode.append(year+"").append(month+"").append(day+"");
		 
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
	 
	
}

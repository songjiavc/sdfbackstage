package com.sdf.manager.order.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.order.dto.OrdersDTO;
import com.sdf.manager.order.entity.Orders;

public interface OrderService {

	public Orders getOrdersById(String id);
	
	/**
	 * 
	* @Description:根据订单编码获取订单数据
	* @author bann@sdfcp.com
	* @date 2015年11月19日 下午1:52:17
	 */
	public Orders getOrdersByCode(String code);
	
	public void update(Orders entity);
	
	public void save(Orders entity);
	
	public QueryResult<Orders> getOrdersList(Class<Orders> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public  List<OrdersDTO> toDTOS(List<Orders> entities);
	
	public  OrdersDTO toDTO(Orders entity);
	
}

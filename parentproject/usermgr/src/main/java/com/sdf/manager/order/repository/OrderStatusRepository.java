package com.sdf.manager.order.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.order.entity.OrderStatus;

public interface OrderStatusRepository extends GenericRepository<OrderStatus, String> {
	
	/**
	 * 
	* @Description:根据状态id获取当前订单状态信息 
	* @author bann@sdfcp.com
	* @date 2015年11月18日 下午3:27:17
	 */
	@Query("select u from OrderStatus u where u.statusId =?1")
	public OrderStatus getOrdersByStatusId(String statusId);
	
	/**
	 * 
	* @Description: 根据父级状态获取其下属的子级订单状态
	* @author bann@sdfcp.com
	* @date 2015年11月18日 下午3:27:33
	 */
	@Query("select u from OrderStatus u where u.parentStatus =?1")
	public List<OrderStatus> getOrdersByParentStatus(String parentStatus);
}

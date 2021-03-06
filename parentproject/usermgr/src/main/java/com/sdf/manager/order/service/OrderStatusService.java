package com.sdf.manager.order.service;

import java.util.List;

import com.sdf.manager.order.entity.OrderNextStatus;
import com.sdf.manager.order.entity.OrderStatus;

public interface OrderStatusService {

	public OrderStatus getOrdersByStatusId(String statusId);
	
	public List<OrderStatus> getOrdersByParentStatus(String parentStatus);
	
	public OrderNextStatus getOrderNextStatusBycurrentStatusId(String currentStatusId,String directionFlag);
}

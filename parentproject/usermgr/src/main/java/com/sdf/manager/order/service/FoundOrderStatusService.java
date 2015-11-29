package com.sdf.manager.order.service;

import java.util.List;

import com.sdf.manager.order.entity.FoundOrderStatus;

public interface FoundOrderStatusService {

	public FoundOrderStatus getFoundOrderStatusById(String id);
	
	public List<FoundOrderStatus> getFoundOrderStatusByOrderId(String orderId);
	
	public void save(FoundOrderStatus entity);
}

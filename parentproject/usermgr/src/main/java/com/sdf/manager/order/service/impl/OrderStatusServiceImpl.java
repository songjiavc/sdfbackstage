package com.sdf.manager.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.order.entity.OrderStatus;
import com.sdf.manager.order.repository.OrderStatusRepository;
import com.sdf.manager.order.service.OrderStatusService;


@Service("orderStatusService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderStatusServiceImpl implements OrderStatusService {

	
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	public OrderStatus getOrdersByStatusId(String statusId) {
		return orderStatusRepository.getOrdersByStatusId(statusId);
	}

	public List<OrderStatus> getOrdersByParentStatus(String parentStatus) {
		return orderStatusRepository.getOrdersByParentStatus(parentStatus);
	}

}

package com.sdf.manager.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.order.entity.OrderNextStatus;
import com.sdf.manager.order.entity.OrderStatus;
import com.sdf.manager.order.repository.OrderNextStatusRepository;
import com.sdf.manager.order.repository.OrderStatusRepository;
import com.sdf.manager.order.service.OrderStatusService;


@Service("orderStatusService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderStatusServiceImpl implements OrderStatusService {

	
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Autowired
	private OrderNextStatusRepository orderNextStatusRepository;
	
	public OrderStatus getOrdersByStatusId(String statusId) {
		return orderStatusRepository.getOrdersByStatusId(statusId);
	}

	public List<OrderStatus> getOrdersByParentStatus(String parentStatus) {
		return orderStatusRepository.getOrdersByParentStatus(parentStatus);
	}
	
	/**
	 * 
	* @Description: 根据当前状态和方向值获取下一状态
	* @author bann@sdfcp.com
	* @date 2015年11月19日 下午1:28:44
	 */
	public OrderNextStatus getOrderNextStatusBycurrentStatusId(String currentStatusId,String directionFlag)
	{
		return orderNextStatusRepository.getOrderNextStatusBycurrentStatusId(currentStatusId, directionFlag);
	}

}

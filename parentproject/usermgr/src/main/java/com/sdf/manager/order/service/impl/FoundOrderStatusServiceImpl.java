package com.sdf.manager.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.order.entity.FoundOrderStatus;
import com.sdf.manager.order.repository.FoundOrderStatusRepository;
import com.sdf.manager.order.service.FoundOrderStatusService;

@Service("foundOrderStatus")
@Transactional(propagation = Propagation.REQUIRED)
public class FoundOrderStatusServiceImpl implements FoundOrderStatusService {

			
	@Autowired
	private FoundOrderStatusRepository foundOrderStatusRepository;
	
	public FoundOrderStatus getFoundOrderStatusById(String id) {

		return foundOrderStatusRepository.getFoundOrderStatusId(id);
	}

	public List<FoundOrderStatus> getFoundOrderStatusByOrderId(String orderId) {
		return foundOrderStatusRepository.getFoundOrderStatusByOrderId(orderId);
	}
	
	public void save(FoundOrderStatus entity)
	{
		foundOrderStatusRepository.save(entity);
	}

}

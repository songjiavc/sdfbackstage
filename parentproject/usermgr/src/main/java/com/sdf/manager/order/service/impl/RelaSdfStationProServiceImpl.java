package com.sdf.manager.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.order.entity.RelaSdfStationProduct;
import com.sdf.manager.order.repository.RelaSdfStaProRepository;
import com.sdf.manager.order.service.RelaSdfStationProService;

@Service("relaSdfStationProService")
@Transactional(propagation=Propagation.REQUIRED)
public class RelaSdfStationProServiceImpl implements RelaSdfStationProService {

	
	@Autowired
	private RelaSdfStaProRepository relaSdfStaProRepository;
	
	public void save(RelaSdfStationProduct entity) {
		
		relaSdfStaProRepository.save(entity);
	}

	public void update(RelaSdfStationProduct entity) {
		
		relaSdfStaProRepository.save(entity);

	}

	public List<RelaSdfStationProduct> getRelaSdfStationProductByOrderId(
			String orderId) {
		return relaSdfStaProRepository.getRelaSdfStationProductByOrderId(orderId);
	}

	public List<RelaSdfStationProduct> getRelaSdfStationProductByStationIdAndProductIdAndType(
			String stationId, String productId, String type) {
		return relaSdfStaProRepository.getRelaSdfStationProductByStationIdAndProductIdAndType(stationId, productId, type);
	}

}

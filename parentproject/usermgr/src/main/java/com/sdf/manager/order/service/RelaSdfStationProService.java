package com.sdf.manager.order.service;

import java.util.List;

import com.sdf.manager.order.entity.RelaSdfStationProduct;

public interface RelaSdfStationProService {

	
	public void save(RelaSdfStationProduct entity);
	
	public void update(RelaSdfStationProduct entity);
	
	public List<RelaSdfStationProduct> getRelaSdfStationProductByOrderId(String orderId);
}

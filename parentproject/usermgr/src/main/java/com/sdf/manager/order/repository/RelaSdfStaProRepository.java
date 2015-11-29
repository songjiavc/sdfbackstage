package com.sdf.manager.order.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.order.entity.Orders;
import com.sdf.manager.order.entity.RelaSdfStationProduct;

public interface RelaSdfStaProRepository extends GenericRepository<RelaSdfStationProduct, String> {
	
	@Query("select u from RelaSdfStationProduct u where  u.isDeleted ='1' and  u.orderId =?1")
	public List<RelaSdfStationProduct> getRelaSdfStationProductByOrderId(String orderId);
	
}

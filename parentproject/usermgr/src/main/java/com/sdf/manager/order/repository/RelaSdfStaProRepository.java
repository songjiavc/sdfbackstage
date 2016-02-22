package com.sdf.manager.order.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.order.entity.Orders;
import com.sdf.manager.order.entity.RelaSdfStationProduct;

public interface RelaSdfStaProRepository extends GenericRepository<RelaSdfStationProduct, String> {
	
	@Query("select u from RelaSdfStationProduct u where  u.isDeleted ='1' and  u.orderId =?1")
	public List<RelaSdfStationProduct> getRelaSdfStationProductByOrderId(String orderId);
	
	/**
	 * 查询站点和产品的正在使用的关联数据，并且按照结束时间排序
	 * @param stationId
	 * @param productId
	 * @param type
	 * @return
	 */
	@Query("select u from RelaSdfStationProduct u where  u.isDeleted ='1' and  u.stationId =?1 and  u.productId =?2 and u.status=?3 order by u.endTime desc")
	public List<RelaSdfStationProduct> getRelaSdfStationProductByStationIdAndProductIdAndType(String stationId,String productId,String type);
	
}

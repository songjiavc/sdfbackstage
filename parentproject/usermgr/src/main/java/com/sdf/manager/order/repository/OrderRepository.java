package com.sdf.manager.order.repository;


import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.order.entity.Orders;

public interface OrderRepository extends GenericRepository<Orders, String> {
	
	
	@Query("select u from Orders u where  u.isDeleted ='1' and  u.id =?1")
	public Orders getOrdersById(String id);
	
	@Query("select u from Orders u where  u.isDeleted ='1' and  u.code =?1")
	public Orders getOrdersByCode(String code);
}

package com.sdf.manager.order.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.order.entity.FoundOrderStatus;

public interface FoundOrderStatusRepository extends GenericRepository<FoundOrderStatus, String> {
	
	/**
	 * 
	* @Description:根据id获取状态详情
	* @author bann@sdfcp.com
	* @date 2015年11月18日 下午3:27:17
	 */
	@Query("select u from FoundOrderStatus u where u.id =?1")
	public FoundOrderStatus getFoundOrderStatusId(String id);
	
	
	/**
	 * 
	* @Description:根据订单id获取当前订单的所有状态流程 
	* @author bann@sdfcp.com
	* @date 2015年11月19日 上午9:19:49
	 */
	@Query("select u from FoundOrderStatus u where u.orders.id =?1")
	public List<FoundOrderStatus> getFoundOrderStatusByOrderId(String orderId);
	
}

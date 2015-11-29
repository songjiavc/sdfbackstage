package com.sdf.manager.order.repository;




import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.order.entity.OrderNextStatus;

public interface OrderNextStatusRepository extends GenericRepository<OrderNextStatus, String> {
	
	
	/**
	 * 
	* @Description: 根据当前状态和方向值获取下一状态
	* @author bann@sdfcp.com
	* @date 2015年11月19日 下午1:28:44
	 */
	@Query("select u from OrderNextStatus u where u.currentStatusId =?1 and u.directionFlag =?2 ")
	public OrderNextStatus getOrderNextStatusBycurrentStatusId(String currentStatusId,String directionFlag);
	
	
	
}

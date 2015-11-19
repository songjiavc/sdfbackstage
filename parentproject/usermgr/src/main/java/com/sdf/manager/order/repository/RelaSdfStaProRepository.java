package com.sdf.manager.order.repository;


import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.order.entity.RelaSdfStationProduct;

public interface RelaSdfStaProRepository extends GenericRepository<RelaSdfStationProduct, String> {
	
	
}

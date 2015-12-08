package com.sdf.manager.product.repository;



import org.springframework.data.jpa.repository.Query;

import com.sdf.manager.common.repository.GenericRepository;
import com.sdf.manager.product.entity.CodeDuration;

public interface CodeDurationRepository extends GenericRepository<CodeDuration, String> {

	@Query("select u from CodeDuration u where  u.id =?1")
	public CodeDuration getCodeDurationById(String id);
}

package com.sdf.manager.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.goods.entity.RelaSdfGoodProduct;
import com.sdf.manager.goods.repository.RelaProAndGoodsRepository;
import com.sdf.manager.goods.service.RelaProAndGoodsService;


@Service("relaAndGoodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class RelaProAndGoodsServiceImpl implements RelaProAndGoodsService {

	
	@Autowired
	private RelaProAndGoodsRepository relaProAndGoodsRepository;
	
	public void save(RelaSdfGoodProduct entity)
	{
		relaProAndGoodsRepository.save(entity);
	}
	
	public void saveRelapGoodsList(List<RelaSdfGoodProduct> entities)
	{
		for (RelaSdfGoodProduct relaSdfGoodProduct : entities) {
			relaProAndGoodsRepository.save(relaSdfGoodProduct);
		}
	}
	
	public void deleteRelapGoodsList(List<RelaSdfGoodProduct> entities)
	{
		for (RelaSdfGoodProduct relaSdfGoodProduct : entities) {
			relaProAndGoodsRepository.delete(relaSdfGoodProduct);
		}
	}
}

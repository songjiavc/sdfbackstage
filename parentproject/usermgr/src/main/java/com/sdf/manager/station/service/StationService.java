package com.sdf.manager.station.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.sdf.manager.common.exception.BizException;
import com.sdf.manager.station.bean.StationBean;
import com.sdf.manager.station.entity.Station;



/** 
  * @ClassName: StationService 
  * @Description: 站点业务层
  * @author songj@sdfcp.com
  * @date 2015年10月21日 上午10:52:29 
  *  
  */
public interface StationService {
	
	/** 
	  * @Description: 获取权限表所有数据
	  * @author songj@sdfcp.com
	  * @date 2015年9月25日 上午8:58:51 
	  * @return 
	  */
	public List<Station> findAll();

	/** 
	  * @Description: 保存更新用户信息
	  * @author songj@sdfcp.com
	  * @date 2015年10月12日 上午11:16:13 
	  * @param user 
	  */
	public void saveOrUpdate(StationBean stationBean) throws BizException;

	/** 
	  * @Description: TODO(这里用一句话描述这个类的作用)
	  * @author songj@sdfcp.com
	  * @date 2015年10月15日 上午10:39:51 
	  * @param id
	  * @return 
	  */
	public Station getSationById(String id);
	
	/** 
	  * @Description: 判断code是否有重复记录
	  * @author songj@sdfcp.com
	  * @date 2015年10月15日 下午1:16:03 
	  * @param code
	  * @return 
	  */
	public Station getStationByCode(String code);

	/** 
	  * @Description:分页查询
	  * @author songj@sdfcp.com
	  * @date 2015年10月13日 下午1:25:25 
	  * @param whereJpql
	  * @param queryParams
	  * @param orderby
	  * @param pageable
	  * @return 
	  */
	public Map<String,Object>  getScrollDataByJpql(Class<Station> entityClass,String whereJpql, Object[] queryParams,LinkedHashMap<String, String> orderby, Pageable pageable);
	
	/** 
	  * @Description: 删除帐号
	  * @author songj@sdfcp.com
	  * @date 2015年10月20日 下午2:20:46 
	  * @param ids
	  * @throws BizException 
	  */
	public void deleteStationByIds(String[] ids) throws BizException;
}

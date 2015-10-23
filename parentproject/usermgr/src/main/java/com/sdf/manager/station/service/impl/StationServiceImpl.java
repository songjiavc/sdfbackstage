package com.sdf.manager.station.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sdf.manager.common.exception.BizException;
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.station.bean.StationBean;
import com.sdf.manager.station.entity.Station;
import com.sdf.manager.station.repository.StationRepository;
import com.sdf.manager.station.service.StationService;
import com.sdf.manager.user.bean.UserRelaRoleBean;
/** 
  * @ClassName: AuthServiceImpl 
  * @Description: 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:51:54 
  *  
  */
@Service("stationService")
@Transactional(propagation=Propagation.REQUIRED)
public class StationServiceImpl implements StationService {
	@Autowired
	private StationRepository stationRepository;
	
	/**
	 * @return
	 */
	public List<Station> findAll() {
		return stationRepository.findAll();
	}

	/**
	 * 
	* @Description: 业务上保存更新方法
	* @author songj@sdfcp.com
	 * @throws BizException 
	* @date 2015年10月15日 下午1:33:45
	 */
	public void saveOrUpdate(StationBean stationBean) throws BizException {
		if(StringUtils.isEmpty(stationBean.getId())){
			//如果是新增判断帐号是否表内重复
			Station stationCode = this.getStationByCode(stationBean.getCode());
			if(null == stationCode){
				Station user = new Station();
				user.setCode(stationBean.getCode());
				user.setName(stationBean.getName());
				user.setPassword(stationBean.getPassword());
				user.setTelephone(stationBean.getTelephone());
				user.setStatus(stationBean.getStatus());
				user.setIsDeleted(Constants.IS_NOT_DELETED);
				user.setCreater("admin");
				user.setCreaterTime(new Date());
				user.setModify("admin");
				user.setModifyTime(new Date());
				stationRepository.save(user);
			}else{
				throw new BizException(0101);
			}
		}else{
			// user.setCode(accountBean.getCode());   修改时登录帐号不允许修改
			Station user = this.getStationById(stationBean.getId());
			user.setName(stationBean.getName());
			user.setPassword(stationBean.getPassword());
			user.setTelephone(stationBean.getTelephone());
			user.setStatus(stationBean.getStatus());
			user.setModify("admin");
			user.setModifyTime(new Date());
			stationRepository.save(user);
		}
	}

	
	/* (非 Javadoc) 
	 * <p>Title: getUserByCode</p> 
	 * <p>Description: </p> 
	 * @param code
	 * @return 
	 * @see com.sdf.manager.user.service.UserService#getUserByCode(java.lang.String) 
	 */
	public Station getStationById(String id)
	{
		Station station =  stationRepository.findOne(id);
		return station;
	}
	

	public Map<String,Object>  getScrollDataByJpql(Class<Station> entityClass,String whereJpql, Object[] queryParams,LinkedHashMap<String, String> orderby, Pageable pageable)
	{
		Map<String,Object> returnData = new HashMap<String,Object>();
		List<StationBean> accountList = new ArrayList<StationBean>();
		QueryResult<Station> userObj = stationRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,orderby, pageable);
		List<Station> userlist = userObj.getResultList();
		Long totalrow = userObj.getTotalRecord();
		for(Station user : userlist){
			StationBean accountBean = new StationBean();
			accountBean.setId(user.getId());
			accountBean.setCode(user.getCode());
			accountBean.setName(user.getName());
			accountBean.setStatus(user.getStatus());
			accountBean.setTelephone(user.getTelephone());
			accountBean.setCreater(user.getCreater());
			accountBean.setCreaterTime(user.getCreaterTime());
			List<UserRelaRoleBean> roleBeanList = new ArrayList<UserRelaRoleBean>();
			/*for(Role role : user.getRoles()){
				RoleBean roleBean = new RoleBean();
				roleBean.setRuleId(role.getId());
				roleBean.setRuleCode(role.getCode());
				roleBean.setRuleName(role.getName());
				roleBeanList.add(roleBean);
			}*/
			UserRelaRoleBean roleBean1 = new UserRelaRoleBean();
			roleBean1.setRoleId("111111");
			roleBean1.setRoleCode("222222");
			roleBean1.setRoleName("333333");
			roleBeanList.add(roleBean1);
			UserRelaRoleBean roleBean2 = new UserRelaRoleBean();
			roleBean2.setRoleId("444444");
			roleBean2.setRoleCode("555555");
			roleBean2.setRoleName("666666");
			roleBeanList.add(roleBean2);
			//accountBean.setRoles(roleBeanList);
			accountList.add(accountBean);
		}
		returnData.put("rows", accountList);
		returnData.put("total", totalrow);
		return returnData;
	}

	public Station getStationByCode(String code) {
		return stationRepository.getStationByCode(code);
	}
	
	
	/* (非 Javadoc) 
	 * <p>Title: deleteAccountByIds</p> 
	 * <p>Description: </p> 
	 * @param ids
	 * @throws BizException 
	 * @see com.sdf.manager.user.service.UserService#deleteAccountByIds(java.lang.String[]) 
	 */
	public void deleteAccountByIds(String[] ids) throws BizException{
		if(ids.length > 0){
			for(String id : ids){
				Station station = this.getStationById(id);
				station.setIsDeleted(Constants.IS_DELETED);
				station.setModify("admin");
				station.setModifyTime(new Date());
				stationRepository.save(station);
			}
		}else{
			throw new BizException(0102);
		}
	}

	public Station getSationById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteStationByIds(String[] ids) throws BizException {
		// TODO Auto-generated method stub
		
	}

}

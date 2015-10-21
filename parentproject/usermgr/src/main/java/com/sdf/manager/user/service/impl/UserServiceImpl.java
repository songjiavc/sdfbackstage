package com.sdf.manager.user.service.impl;

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
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.user.bean.AccountBean;
import com.sdf.manager.user.bean.UserRleaRoleBean;
import com.sdf.manager.user.entity.User;
import com.sdf.manager.user.repository.UserRepository;
import com.sdf.manager.user.service.UserService;
/** 
  * @ClassName: AuthServiceImpl 
  * @Description: 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:51:54 
  *  
  */
@Service("userService")
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * @return
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * 
	* @Description: 业务上保存更新方法
	* @author songj@sdfcp.com
	 * @throws BizException 
	* @date 2015年10月15日 下午1:33:45
	 */
	public void saveOrUpdate(AccountBean accountBean) throws BizException {
		if(StringUtils.isEmpty(accountBean.getId())){
			//如果是新增判断帐号是否表内重复
			User userCode = this.getUserByCode(accountBean.getCode());
			if(null == userCode){
				User user = new User();
				user.setCode(accountBean.getCode());
				user.setName(accountBean.getName());
				user.setPassword(accountBean.getPassword());
				user.setTelephone(accountBean.getTelephone());
				user.setStatus(accountBean.getStatus());
				user.setIsDeleted(Constants.IS_NOT_DELETED);
				user.setCreater("admin");
				user.setCreaterTime(new Date());
				user.setModify("admin");
				user.setModifyTime(new Date());
				userRepository.save(user);
			}else{
				throw new BizException(0101);
			}
		}else{
			// user.setCode(accountBean.getCode());   修改时登录帐号不允许修改
			User user = this.getUserById(accountBean.getId());
			user.setName(accountBean.getName());
			user.setPassword(accountBean.getPassword());
			user.setTelephone(accountBean.getTelephone());
			user.setStatus(accountBean.getStatus());
			user.setModify("admin");
			user.setModifyTime(new Date());
			userRepository.save(user);
		}
	}

	
	/* (非 Javadoc) 
	 * <p>Title: getUserByCode</p> 
	 * <p>Description: </p> 
	 * @param code
	 * @return 
	 * @see com.sdf.manager.user.service.UserService#getUserByCode(java.lang.String) 
	 */
	public User getUserById(String id)
	{
		User user =  userRepository.findOne(id);
		return user;
	}
	

	public Map<String,Object> getScrollDataByJpql(Class<User> entityClass,String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby, Pageable pageable) {
		Map<String,Object> returnData = new HashMap<String,Object>();
		List<AccountBean> accountList = new ArrayList<AccountBean>();
		QueryResult<User> userObj = userRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,orderby, pageable);
		List<User> userlist = userObj.getResultList();
		Long totalrow = userObj.getTotalRecord();
		for(User user : userlist){
			AccountBean accountBean = new AccountBean();
			accountBean.setId(user.getId());
			accountBean.setCode(user.getCode());
			accountBean.setName(user.getName());
			accountBean.setStatus(user.getStatus());
			accountBean.setTelephone(user.getTelephone());
			accountBean.setCreater(user.getCreater());
			accountBean.setCreaterTime(user.getCreaterTime());
			List<UserRleaRoleBean> roleBeanList = new ArrayList<UserRleaRoleBean>();
			/*for(Role role : user.getRoles()){
				RoleBean roleBean = new RoleBean();
				roleBean.setRuleId(role.getId());
				roleBean.setRuleCode(role.getCode());
				roleBean.setRuleName(role.getName());
				roleBeanList.add(roleBean);
			}*/
			UserRleaRoleBean roleBean1 = new UserRleaRoleBean();
			roleBean1.setRoleId("111111");
			roleBean1.setRoleCode("222222");
			roleBean1.setRoleName("333333");
			roleBeanList.add(roleBean1);
			UserRleaRoleBean roleBean2 = new UserRleaRoleBean();
			roleBean2.setRoleId("444444");
			roleBean2.setRoleCode("555555");
			roleBean2.setRoleName("666666");
			roleBeanList.add(roleBean2);
			accountBean.setRoles(roleBeanList);
			accountList.add(accountBean);
		}
		returnData.put("rows", accountList);
		returnData.put("total", totalrow);
		return returnData;
	}

	public User getUserByCode(String code) {
		return userRepository.getUserByCode(code);
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
				User user = this.getUserById(id);
				user.setIsDeleted(Constants.IS_DELETED);
				user.setModify("admin");
				user.setModifyTime(new Date());
				userRepository.save(user);
			}
		}else{
			throw new BizException(0102);
		}
	}

}

package com.sdf.manager.user.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sdf.manager.common.util.BeanUtil;
import com.sdf.manager.common.util.Constants;
import com.sdf.manager.common.util.DateUtil;
import com.sdf.manager.common.util.QueryResult;
import com.sdf.manager.product.application.dto.ProductDto;
import com.sdf.manager.product.entity.City;
import com.sdf.manager.product.entity.Product;
import com.sdf.manager.product.entity.ProductDL;
import com.sdf.manager.product.entity.ProductXL;
import com.sdf.manager.product.entity.ProductZL;
import com.sdf.manager.product.entity.Province;
import com.sdf.manager.user.bean.AuthorityBean;
import com.sdf.manager.user.dto.AuthorityDTO;
import com.sdf.manager.user.entity.Authority;
import com.sdf.manager.user.entity.Role;
import com.sdf.manager.user.repository.AuthRepository;
import com.sdf.manager.user.service.AuthService;

/** 
  * @ClassName: AuthServiceImpl 
  * @Description: 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:51:54 
  *  
  */
@Service("authService")
@Transactional(propagation=Propagation.REQUIRED)
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthRepository authRepository;
	
	/**
	 * @return
	 */
	public List<Authority> findAll() {
		return authRepository.findAll();
	}

	/**
	 * 
	* @Description: TODO(保存权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午2:33:45
	 */
	public void save(Authority authority) {
		authRepository.save(authority);
		
	}

	
	/**
	 * 
	* @Description: TODO(根据code获取权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午3:37:34
	 */
	public Authority getAuthorityByCode(String code)
	{
		Authority authority =  authRepository.getAuthorityByCode(code);
		
		return authority;
	}
	
	/***
	 * 
	* @Description: TODO(获取权限数据列表) 
	* @author bann@sdfcp.com
	* @date 2015年10月10日 下午3:04:33
	 */
	public List<Authority> getAuthorityList(AuthorityBean authorityBean)
	{
		List<Authority> authorities = new ArrayList<Authority>();
		
		authorities = authRepository.findAll();
		
			
		return authorities;
	}
	
	/**
	 * 
	* @Description: TODO(带分页条件模糊查询权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月14日 上午8:55:41
	 */
	public QueryResult<Authority> getAuthList(Class<Authority> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable)
	{
		
		QueryResult<Authority> authlist = authRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		return authlist;
	}
	
	
	
	/**
	 * 
	* @Description: 将权限实体转换为dto
	* @author bann@sdfcp.com
	* @date 2015年11月24日 上午9:13:43
	 */
	public  AuthorityDTO toDTO(Authority entity) {
		AuthorityDTO dto = new AuthorityDTO();
		try {
			BeanUtil.copyBeanProperties(dto, entity);
			
			if(null != entity.getRoles() && entity.getRoles().size()>0)
			{
				String connectRole = "1";
				dto.setConnectRole(connectRole);
				StringBuffer bindRoles = new StringBuffer();
				String roleName = "";
				for (Role role : entity.getRoles()) {
					
					roleName = role.getName();
					if(bindRoles.length()==0)
					{
						bindRoles.append(roleName);
					}
					else
					{
						bindRoles.append("、").append(roleName);
					}
					
				}
				dto.setBindRoles(bindRoles.toString());
			}
			else
			{
				String connectRole = "0";
				dto.setConnectRole(connectRole);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
					
		return dto;
	}
	
	/**
	 * 
	* @Description:批量将权限实体转换为dto
	* @author bann@sdfcp.com
	* @date 2015年11月24日 上午9:13:36
	 */
	public  List<AuthorityDTO> toDTOS(List<Authority> entities) {
		List<AuthorityDTO> dtos = new ArrayList<AuthorityDTO>();
		AuthorityDTO dto;
		for (Authority entity : entities) 
		{
			dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}
 
	
	
	
	

}

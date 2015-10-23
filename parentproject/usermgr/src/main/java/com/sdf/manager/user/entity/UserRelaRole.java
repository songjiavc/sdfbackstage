package com.sdf.manager.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/** 
  * @ClassName: UserRelaRole 
  * @Description: 用户和角色关联关系表
  * @author songj@sdfcp.com
  * @date 2015年10月19日 上午9:21:36 
  *  
  */
@Entity
@Table(name="RELA_SDF_USER_ROLE")
public class UserRelaRole implements Serializable 
{
	
	
	/** 
	  * @Fields serialVersionUID : 
	  */ 
	private static final long serialVersionUID = 7692132908491601627L;

	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="USER_ID",length=45)
	private String userId;
	
	@Column(name="ROLE_ID",length=45)
	private String roleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}

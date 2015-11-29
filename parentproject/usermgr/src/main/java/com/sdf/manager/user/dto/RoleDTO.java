package com.sdf.manager.user.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class RoleDTO {

	private String id;
	
	private String code;
	
	private String name;
	
	private String parentRole;
	
	private String isSystem;//是否为系统级数据，不可以进行删除等操作
	
	
	private String parentRolename;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getParentRole() {
		return parentRole;
	}


	public void setParentRole(String parentRole) {
		this.parentRole = parentRole;
	}


	public String getIsSystem() {
		return isSystem;
	}


	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}


	public String getParentRolename() {
		return parentRolename;
	}


	public void setParentRolename(String parentRolename) {
		this.parentRolename = parentRolename;
	}
	
	
}

package com.sdf.manager.user.bean;

public class RoleBean 
{
	private String id;
	
	private String code;
	
	private String name;
	
	private String parentRole;
	
	private String parentRolename;//上级角色名称
	
//	private String status;
	
	private String isDeleted;//是否删除
	
	private int page;//当前页
	
	private int rows;//行数

	
	
	
	public String getParentRolename() {
		return parentRolename;
	}

	public void setParentRolename(String parentRolename) {
		this.parentRolename = parentRolename;
	}

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

//	public String getStatus() {
//	return status;
//}
//
//public void setStatus(String status) {
//	this.status = status;
//}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
}

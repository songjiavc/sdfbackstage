package com.sdf.manager.user.dto;


public class AuthorityDTO {

	private String id;
	
	private String code;
	
	private String authName;
	
	private String parentAuth;
	
	private String url;
	
	private String authImg;
	
	private String status;
	
	private String isSystem;//是否为系统级数据，不可以进行删除等操作
	
	
	private String bindRoles;//绑定的角色
	
	private String connectRole="0";//是否绑定了角色（0：未绑定  1：绑定）
	
	
	

	public String getBindRoles() {
		return bindRoles;
	}

	public void setBindRoles(String bindRoles) {
		this.bindRoles = bindRoles;
	}

	public String getConnectRole() {
		return connectRole;
	}

	public void setConnectRole(String connectRole) {
		this.connectRole = connectRole;
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

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getParentAuth() {
		return parentAuth;
	}

	public void setParentAuth(String parentAuth) {
		this.parentAuth = parentAuth;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthImg() {
		return authImg;
	}

	public void setAuthImg(String authImg) {
		this.authImg = authImg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}
	
	
}

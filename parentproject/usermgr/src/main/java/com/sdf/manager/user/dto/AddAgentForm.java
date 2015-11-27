package com.sdf.manager.user.dto;

public class AddAgentForm 
{
	//id
	private String id;
	//代理登录帐号
	private String addFormAgentCode;
	//代理类型
	private String addFormAgentStyle;
	//省
	private String addFormProvince;
	//市
	private String addFormCity;
	//区
	private String addFormRegion;
	//详细地址
	private String addFormAddress;
	//代理姓名
	private String addFormName;
	//代理手机号
	private String addFormTelephone;
	//登录密码
	private String password;
	//上级id
	private String addFormParentId;
	
	/** 
	  * @Fields status : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private String status;
	
	/** 
	  * @Fields creater : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private String creater;
	
	/** 
	  * @Fields createrTime : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private String createrTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddFormAgentCode() {
		return addFormAgentCode;
	}

	public void setAddFormAgentCode(String addFormAgentCode) {
		this.addFormAgentCode = addFormAgentCode;
	}

	public String getAddFormAgentStyle() {
		return addFormAgentStyle;
	}

	public void setAddFormAgentStyle(String addFormAgentStyle) {
		this.addFormAgentStyle = addFormAgentStyle;
	}

	public String getAddFormProvince() {
		return addFormProvince;
	}

	public void setAddFormProvince(String addFormProvince) {
		this.addFormProvince = addFormProvince;
	}

	public String getAddFormCity() {
		return addFormCity;
	}

	public void setAddFormCity(String addFormCity) {
		this.addFormCity = addFormCity;
	}

	public String getAddFormRegion() {
		return addFormRegion;
	}

	public void setAddFormRegion(String addFormRegion) {
		this.addFormRegion = addFormRegion;
	}

	public String getAddFormAddress() {
		return addFormAddress;
	}

	public void setAddFormAddress(String addFormAddress) {
		this.addFormAddress = addFormAddress;
	}

	public String getAddFormName() {
		return addFormName;
	}

	public void setAddFormName(String addFormName) {
		this.addFormName = addFormName;
	}

	public String getAddFormTelephone() {
		return addFormTelephone;
	}

	public void setAddFormTelephone(String addFormTelephone) {
		this.addFormTelephone = addFormTelephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddFormParentId() {
		return addFormParentId;
	}

	public void setAddFormParentId(String addFormParentId) {
		this.addFormParentId = addFormParentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreaterTime() {
		return createrTime;
	}

	public void setCreaterTime(String createrTime) {
		this.createrTime = createrTime;
	}
	
	
	
	
}

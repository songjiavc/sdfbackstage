package com.sdf.manager.user.dto;

public class AgentListDto 
{
	//id
	private String id;
	//代理登录帐号
	private String agentCode;
	//代理类型
	private String agentStyle;
	//省
	private String province;
	//市
	private String city;
	//区
	private String region;
	//详细地址
	private String address;
	//代理姓名
	private String name;
	//代理手机号
	private String telephone;
	//登录密码
	private String password;
	//上级id
	private String parentName;
	
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

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentStyle() {
		return agentStyle;
	}

	public void setAgentStyle(String agentStyle) {
		this.agentStyle = agentStyle;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

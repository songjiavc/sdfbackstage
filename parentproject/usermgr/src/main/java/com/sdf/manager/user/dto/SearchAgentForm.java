package com.sdf.manager.user.dto;

/** 
  * @ClassName: SearchAgentForm 
  * @Description: 查询列表和查询条件使用的dto
  * @author songj@sdfcp.com
  * @date 2015年11月25日 上午9:41:18 
  *  
  */
public class SearchAgentForm 
{
	/** 
	  * @Fields id : 主键id
	  */ 
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
	
	
}

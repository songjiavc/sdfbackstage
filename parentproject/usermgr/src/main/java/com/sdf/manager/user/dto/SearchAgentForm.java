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
	  */ 
	//代理登录帐号
	private String searchAgentNumber;
	//省
	private String searchFormProvince;
	//市
	private String city;
	//区
	private String region;
	//详细地址
	private String address;
	//代理姓名
	private String searchFormName;
	//代理手机号
	private String searchFormTelephone;
	public String getSearchAgentNumber() {
		return searchAgentNumber;
	}
	public void setSearchAgentNumber(String searchAgentNumber) {
		this.searchAgentNumber = searchAgentNumber;
	}
	public String getSearchFormProvince() {
		return searchFormProvince;
	}
	public void setSearchFormProvince(String searchFormProvince) {
		this.searchFormProvince = searchFormProvince;
	}
	public String getSearchFormName() {
		return searchFormName;
	}
	public void setSearchFormName(String searchFormName) {
		this.searchFormName = searchFormName;
	}
	public String getSearchFormTelephone() {
		return searchFormTelephone;
	}
	public void setSearchFormTelephone(String searchFormTelephone) {
		this.searchFormTelephone = searchFormTelephone;
	}
	
	
}

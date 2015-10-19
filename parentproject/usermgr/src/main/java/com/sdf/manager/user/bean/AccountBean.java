package com.sdf.manager.user.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountBean 
{
	private String id;
	
	private String code;
	
	private String name;
	
	private String password;
	
	private String parentName;
	
	private String status;
	
	private List<RuleBean> ruleBeanList = new ArrayList<RuleBean>();

	private String telephone;
	
	private String creater;
	
	private Date createrTime;
	
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreaterTime() {
		return createrTime;
	}

	public void setCreaterTime(Date createrTime) {
		this.createrTime = createrTime;
	}

	public List<RuleBean> getRuleBeanList() {
		return ruleBeanList;
	}

	public void setRuleBeanList(List<RuleBean> ruleBeanList) {
		this.ruleBeanList = ruleBeanList;
	}
	
}

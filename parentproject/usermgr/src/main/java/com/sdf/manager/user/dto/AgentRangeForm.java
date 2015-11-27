package com.sdf.manager.user.dto;

import java.io.Serializable;
import java.util.List;

import com.sdf.manager.user.entity.AgentProject;

public class AgentRangeForm implements Serializable 
{
	
	/** 
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private static final long serialVersionUID = -1620009923321794541L;

	private String id;
	
	private String agentId;
	
	private String provinceCode;
	
	private String cityCode;
	
	private List<AgentProject> agentProjetList;
	
	private String creater;
	
	private String createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public List<AgentProject> getAgentProjetList() {
		return agentProjetList;
	}

	public void setAgentProjetList(List<AgentProject> agentProjetList) {
		this.agentProjetList = agentProjetList;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}

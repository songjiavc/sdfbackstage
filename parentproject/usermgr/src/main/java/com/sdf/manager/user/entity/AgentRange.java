package com.sdf.manager.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_SDF_AGENT_RANGE")
public class AgentRange implements Serializable 
{
	
	/** 
	  * @Fields serialVersionUID : 使用逆向工程时使用
	  */ 
	private static final long serialVersionUID = -4459647390892572245L;

	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="AGENT_ID")
	private String agentId;
	
	@Column(name="PROVINCE_CODE")
	private String provinceCode;
	
	@Column(name="CITY_CODE")
	private String cityCode;
	
	@Column(name="AGENT_PROJETC_CODE")
	private String agentProjetcCode;
	
	@Column(name="CREATER")
	private String creater;
	
	@Column(name="CREATE_DATE")
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

	public String getAgentProjetcCode() {
		return agentProjetcCode;
	}

	public void setAgentProjetcCode(String agentProjetcCode) {
		this.agentProjetcCode = agentProjetcCode;
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

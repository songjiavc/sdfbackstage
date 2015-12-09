package com.sdf.manager.product.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="T_SDF_CODE_DURATION")
public class CodeDuration  {
	
	@Id
	@Column(name="ID", nullable=false, length=11)
	private String id;//走势图产品的使用年限（1:半年;2:一年;3:两年;4:三年;）
	
	@Column(name="CODE", length=45)
	private String code;
	
	@Column(name="NAME", length=45)
	private String name;
	
	@Column(name="NUM_OF_DAYS", length=45)
	private String numOfDays;//天数
	
	@Column(name="CREATER", length=45)
	protected String creater;
	
	@Column(name="CREATER_TIME")
	protected Timestamp createrTime;


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

	public String getNumOfDays() {
		return numOfDays;
	}

	public void setNumOfDays(String numOfDays) {
		this.numOfDays = numOfDays;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Timestamp getCreaterTime() {
		return createrTime;
	}

	public void setCreaterTime(Timestamp createrTime) {
		this.createrTime = createrTime;
	}
	
	

	
	
}

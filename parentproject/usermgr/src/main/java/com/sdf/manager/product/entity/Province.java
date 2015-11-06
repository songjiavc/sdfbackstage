package com.sdf.manager.product.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sdf.manager.user.entity.BaseEntiry;

@Entity
@Table(name="T_SDF_PROVINCE")
public class Province extends BaseEntiry implements Serializable 
{
	/** 
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private static final long serialVersionUID = 5207440618824306396L;

	@Id
	@Column(name="PID", nullable=false, length=11)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="Pcode")
	private String pcode;
	
	@Column(name="Pname")
	private String pname;
	
	@Column(name="NationCode")
	private String nationCode;
	
	@Column(name="PrecordCreationDate")
	private Timestamp precordCreationDate;
	
	@Column(name="PrecordCreator")
	private String precordCreator;
	
	@Column(name="PrecordVersion")
	private String precordVersion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}


	public Timestamp getPrecordCreationDate() {
		return precordCreationDate;
	}

	public void setPrecordCreationDate(Timestamp precordCreationDate) {
		this.precordCreationDate = precordCreationDate;
	}

	public String getPrecordCreator() {
		return precordCreator;
	}

	public void setPrecordCreator(String precordCreator) {
		this.precordCreator = precordCreator;
	}

	public String getPrecordVersion() {
		return precordVersion;
	}

	public void setPrecordVersion(String precordVersion) {
		this.precordVersion = precordVersion;
	}
	
	
	
	
	
	
	
	
	
}

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
@Table(name="T_SDF_CITY")
public class City extends BaseEntiry implements Serializable {

	/** 
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private static final long serialVersionUID = 3995552752646634059L;

	@Id
	@Column(name="CID", nullable=false, length=11)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="Ccode")
	private String ccode;
	
	@Column(name="Cname")
	private String cname;
	
	@Column(name="ProvinceCode")
	private String provinceCode;
	
	@Column(name="CrecordCreationDate")
	private Timestamp crecordCreationDate;
	
	@Column(name="CrecordCreator")
	private String crecordCreator;
	
	@Column(name="CrecordVersion")
	private String crecordVersion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Timestamp getCrecordCreationDate() {
		return crecordCreationDate;
	}

	public void setCrecordCreationDate(Timestamp crecordCreationDate) {
		this.crecordCreationDate = crecordCreationDate;
	}

	public String getCrecordCreator() {
		return crecordCreator;
	}

	public void setCrecordCreator(String crecordCreator) {
		this.crecordCreator = crecordCreator;
	}

	public String getCrecordVersion() {
		return crecordVersion;
	}

	public void setCrecordVersion(String crecordVersion) {
		this.crecordVersion = crecordVersion;
	}
	
	
	
	
	
	
	
}

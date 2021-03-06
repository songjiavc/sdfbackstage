package com.sdf.manager.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sdf.manager.user.entity.BaseEntiry;


@Entity
@Table(name="T_SDF_CODE_XL_PRODUCT")
public class ProductXL extends BaseEntiry {

	@Id
	@Column(name="ID", nullable=false, length=11)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DL_CODE")
	private String dlCode;
	
	@Column(name="ZL_CODE")
	private String zlCode;

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

	public String getDlCode() {
		return dlCode;
	}

	public void setDlCode(String dlCode) {
		this.dlCode = dlCode;
	}

	public String getZlCode() {
		return zlCode;
	}

	public void setZlCode(String zlCode) {
		this.zlCode = zlCode;
	}
	
	
	
}

package com.sdf.manager.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/** 
  * @ClassName: Student 
  * @Description: 获取
  * @author songj@sdfcp.com
  * @date 2015年9月23日 下午5:27:11 
  *  
  */
@Entity
@Table(name="T_SDF_AUTHORITY")
public class Authority extends BaseEntiry{
	
	@Column(name="CODE")
	protected String code;
	
	@Column(name="PARANT_AUTH_ID")
	protected String parentAuth;
	
	@Column(name="URL")
	protected String url;
	
	@Column(name="AUTH_IMG")
	protected String authImg;
	
	@Column(name="STATUS")
	protected String status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentAuth() {
		return parentAuth;
	}

	public void setParentAuth(String parentAuth) {
		this.parentAuth = parentAuth;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthImg() {
		return authImg;
	}

	public void setAuthImg(String authImg) {
		this.authImg = authImg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}

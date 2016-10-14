package com.sdf.manager.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/** 
  * @ClassName: Student 
  * @Description: 获取
  * @author songj@sdfcp.com
  * @date 2015年9月23日 下午5:27:11 
  *  
  */
@Entity
@Table(name="T_SONG_LOGINSTATUS")
public class LoginStatus implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4972259648762599109L;

	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="STATION_CODE")
	private String stationCode;
	
	@Column(name="IS_LOGIN")
	private String isLogin;
	
	@Column(name="UPDATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updateTime;

	public String getId() {
		return id;
	}

	public String getStationCode() {
		return stationCode;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}

package com.sdf.manager.goods.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sdf.manager.user.entity.BaseEntiry;

@Entity
@Table(name="T_SDF_GOODS")
public class Goods extends BaseEntiry{

	
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="CODE", length=45)
	private String code;//商品编号全系统唯一（规则为地市编号+当前日期+流水号）
	
	
	@Column(name="NAME", length=45)
	private String name;//商品名称
	
	
	@Column(name="STATUS", length=2)
	private String status;//商品状态(0:待上架;1:上架;2:下架)
	
	@Column(name="STATUS_TIME")
	private Timestamp statusTime;//当前状态时间
	
	@Column(name="PROVINCE_DM", length=45)
	private String provinceDm;//商品价格（商品中所有产品实际售卖价格之和）
	
	@Column(name="CITY_DM", length=45)
	private String cityDm;
	
	@Column(name="PRICE", length=45)
	private String price;
	
	@Column(name="GOODS_DESPRITION", length=155)
	private String goodsDesprition;
	
	
	
	
	public String getGoodsDesprition() {
		return goodsDesprition;
	}

	public void setGoodsDesprition(String goodsDesprition) {
		this.goodsDesprition = goodsDesprition;
	}

	@OneToMany(mappedBy = "goods", fetch = FetchType.LAZY) 
	private List<RelaSdfGoodProduct> goodAndproduct;
	
	

	public List<RelaSdfGoodProduct> getGoodAndproduct() {
		return goodAndproduct;
	}

	public void setGoodAndproduct(List<RelaSdfGoodProduct> goodAndproduct) {
		this.goodAndproduct = goodAndproduct;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}

	public String getProvinceDm() {
		return provinceDm;
	}

	public void setProvinceDm(String provinceDm) {
		this.provinceDm = provinceDm;
	}

	public String getCityDm() {
		return cityDm;
	}

	public void setCityDm(String cityDm) {
		this.cityDm = cityDm;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
	
}

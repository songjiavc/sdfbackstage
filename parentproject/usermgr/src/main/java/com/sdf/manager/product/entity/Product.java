package com.sdf.manager.product.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sdf.manager.goods.entity.RelaSdfGoodProduct;
import com.sdf.manager.user.entity.BaseEntiry;


@Entity
@Table(name="T_SDF_PRODUCT")
public class Product extends BaseEntiry implements Serializable{

	/** 
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private static final long serialVersionUID = 7143683235386425675L;
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="CODE")
	private String code;
	
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="LOTTERY_TYPE", length=10)
	private String lotteryType;//彩票种类（0：体彩 1：福彩）
	
	
	@Column(name="STATUS_TIME")
	private String statusTime;
	
	@Column(name="PRICE")
	private String price;//参考价格
	
	@Column(name="PROVINCE_DM")
	private String provinceDm;//省份
	
	@Column(name="CITY_DM")
	private String cityDm;//市

	@Column(name="CPDL_DM")
	private String cpdlDm;//产品大类
	
	@Column(name="CPZL_DM")
	private String cpzlDm;//产品中类
	
	@Column(name="CPXL_DM")
	private String cpxlDm;//产品小类
	
	@Column(name="PRODUCT_DESPRITION")
	private String productDesprition;//产品描述
	
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY) 
	private List<RelaSdfGoodProduct> goodAndproduct;
	
	
	
	
	

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

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

	public String getProductDesprition() {
		return productDesprition;
	}

	public void setProductDesprition(String productDesprition) {
		this.productDesprition = productDesprition;
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

	public String getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getCpdlDm() {
		return cpdlDm;
	}

	public void setCpdlDm(String cpdlDm) {
		this.cpdlDm = cpdlDm;
	}

	public String getCpzlDm() {
		return cpzlDm;
	}

	public void setCpzlDm(String cpzlDm) {
		this.cpzlDm = cpzlDm;
	}

	public String getCpxlDm() {
		return cpxlDm;
	}

	public void setCpxlDm(String cpxlDm) {
		this.cpxlDm = cpxlDm;
	}
	
	
	
	
	
}

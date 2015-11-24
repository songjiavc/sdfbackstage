package com.sdf.manager.goods.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Transient;

import com.sdf.manager.order.entity.Orders;
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
	
	
	@Transient
	//表间关联的主控方的配置
	//@JoinTable描述了多对多关系的数据表关系。name属性指定中间表名称，joinColumns定义中间表与Role表的外键关系。
    //中间表RELA_SDF_AUTHORITY_ROLE的ROLE_ID列是Teacher表的主键列对应的外键列，inverseJoinColumns属性定义了中间表与另外一端(Authority)的外键关系。
    //属性referencedColumnName标注的是所关联表中的字段名，若不指定则使用的所关联表的主键字段名作为外键。 
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "RELA_SDF_ORDER_GOOD", 
            joinColumns = {  @JoinColumn(name = "GOOD_ID", referencedColumnName = "id")  }, 
            inverseJoinColumns = {@JoinColumn(name = "ORDER_ID", referencedColumnName = "id") })
	private List<Orders> orders;
	
	

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
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

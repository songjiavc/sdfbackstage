package com.sdf.manager.goods.dto;

import java.sql.Timestamp;
import java.util.List;

import com.sdf.manager.goods.entity.RelaSdfGoodProduct;

public class GoodsDTO {



	private String id;
	private String name;
	private String code;//产品编码
	private String provinceDm;//省份
	private String cityDm;//市
	private String price;//参考价格
	
	private String provinceName;//省份名称
	private String cityName;//市名称
	
	private String status;//商品状态(0:待上架;1:上架;2:下架)
	
	private Timestamp statusTime;

	
	private String createTime;//创建时间
	
	private String goodsDesprition;//商品描述
	
	private String probation;//试用期
	
	private String connectOrders = "0";//是否与有效订单数据关联（0：未关联  1：关联）
	
	
	private List<RelaSdfGoodProduct> goodAndproduct ;
	
	
	
	
	public String getConnectOrders() {
		return connectOrders;
	}

	public void setConnectOrders(String connectOrders) {
		this.connectOrders = connectOrders;
	}

	public List<RelaSdfGoodProduct> getGproducts() {
		return goodAndproduct;
	}

	public void setGproducts(List<RelaSdfGoodProduct> goodAndproduct) {
		this.goodAndproduct = goodAndproduct;
	}
	
	
	
	
	
	public String getProbation() {
		return probation;
	}

	public void setProbation(String probation) {
		this.probation = probation;
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

	public String getGoodsDesprition() {
		return goodsDesprition;
	}

	public void setGoodsDesprition(String goodsDesprition) {
		this.goodsDesprition = goodsDesprition;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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



	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	

}

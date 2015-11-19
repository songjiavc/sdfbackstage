package com.sdf.manager.order.dto;

import java.sql.Timestamp;
import java.util.List;

import com.sdf.manager.goods.entity.RelaSdfGoodProduct;

public class OrdersDTO {



	private String id;
	private String name;
	private String code;//产品编码
	private String provinceDm;//省份
	private String price;//总价
	private String stationId;//站点号码：关联用户表的站点类数据
	
	
	private String status;//订单状态
	private String statusName;//订单状态名称
	
	private Timestamp statusTime;
	
	private String transCost;//运费

	
	private String createTime;//创建时间
	
	private String payMode;//支付方式，下拉框：0-现金支付 1-转账支付
	
	private String receiveAddr;//收件人地址
	
	private String receiveTele;//收件人电话
	
	private String creator;//订单创建人
	
	private String operator;//最近订单状态操作订单人
	
	
	
	
	
	

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getTransCost() {
		return transCost;
	}

	public void setTransCost(String transCost) {
		this.transCost = transCost;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getReceiveAddr() {
		return receiveAddr;
	}

	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
	}

	public String getReceiveTele() {
		return receiveTele;
	}

	public void setReceiveTele(String receiveTele) {
		this.receiveTele = receiveTele;
	}
	
	
	
}

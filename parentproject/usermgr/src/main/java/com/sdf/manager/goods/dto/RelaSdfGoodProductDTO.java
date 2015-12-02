package com.sdf.manager.goods.dto;




public class RelaSdfGoodProductDTO {

	private String id;
	
	
	private String goodId;
	
	private String productId;
	
	private String probation;//代理设置试用期天数的最大值
	
	private String price;//实际售卖价格
	
	private String name;//产品名称
	private String lotteryType;//产品彩票种类（1：体彩 2：福彩）
	
	private String code;//产品编码
	
	private String orderprobation;//订单中设置的试用期
	
	private String stationOfPro;
	
	
	
	

	
	
	public String getStationOfPro() {
		return stationOfPro;
	}

	public void setStationOfPro(String stationOfPro) {
		this.stationOfPro = stationOfPro;
	}

	public String getOrderprobation() {
		return orderprobation;
	}

	public void setOrderprobation(String orderprobation) {
		this.orderprobation = orderprobation;
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

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProbation() {
		return probation;
	}

	public void setProbation(String probation) {
		this.probation = probation;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
	
	
}

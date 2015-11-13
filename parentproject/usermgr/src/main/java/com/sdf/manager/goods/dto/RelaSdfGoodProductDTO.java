package com.sdf.manager.goods.dto;




public class RelaSdfGoodProductDTO {

	private String id;
	
	
	private String goodId;
	
	private String productId;
	
	private String probation;//代理设置试用期天数的最大值
	
	private String price;//实际售卖价格
	
	


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

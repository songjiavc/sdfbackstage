package com.sdf.manager.goods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sdf.manager.product.entity.Product;



@Entity
@Table(name="RELA_SDF_GOOD_PRODUCT")
public class RelaSdfGoodProduct {

	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	
//	@Column(name="GOOD_ID", length=45)
//	private String goodId;
	
//	@Column(name="PRODUCT_ID", length=45)
//	private String productId;
	
	@Column(name="PROBATION", length=45)
	private String probation;//代理设置试用期天数的最大值
	
	@Column(name="PRICE", length=45)
	private String price;//实际售卖价格
	
    @ManyToOne  
    @JoinColumn(name = "GOOD_ID", referencedColumnName = "id")
    private Goods goods;  
    
    @ManyToOne  
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "id")
    private Product product; 
	
	

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public String getGoodId() {
//		return goodId;
//	}
//
//	public void setGoodId(String goodId) {
//		this.goodId = goodId;
//	}

//	public String getProductId() {
//		return productId;
//	}
//
//	public void setProductId(String productId) {
//		this.productId = productId;
//	}

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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	
	
}

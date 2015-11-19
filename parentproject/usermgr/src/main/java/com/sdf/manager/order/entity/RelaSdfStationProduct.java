package com.sdf.manager.order.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sdf.manager.product.entity.Product;
import com.sdf.manager.user.entity.BaseEntiry;



@Entity
@Table(name="RELA_SDF_STATION_PRODUCT")
public class RelaSdfStationProduct extends BaseEntiry {

	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="STATION_ID", length=45, nullable=false)
	private String stationId;//站点id
	
	@Column(name="PRODUCT_ID", length=45, nullable=false)
	private String productId;//商品与产品关联表的id
	
	@Column(name="START_TIME")
	private Timestamp startTime;//开始时间
	
	@Column(name="END_TIME")
	private Timestamp endTime;//结束时间（根据购买时间自动计算）
	
	@Column(name="TYPE", length=10)
	private String type;//类型（0：试用;1:正式使用
	
	
	/*@ManyToOne  
    @JoinColumn(name = "STATION_ID", referencedColumnName = "id")
    private User user;  
    
    @ManyToOne  
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "id")
    private Product product;*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	

	
	
}

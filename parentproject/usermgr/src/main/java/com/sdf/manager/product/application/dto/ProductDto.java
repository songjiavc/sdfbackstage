package com.sdf.manager.product.application.dto;

import java.util.Date;
import java.util.List;

import com.sdf.manager.goods.entity.RelaSdfGoodProduct;

/**
 * ※DTO是用来传递数据的，所以无论是前台的数据传递还是前台和后台的数据交互都应该是用dto进行交互而不是bean※
 * 
  * @ClassName: ProductDto 
  * @Description: 产品dto
  * @author bann@sdfcp.com
  * @date 2015年11月3日 下午4:09:10 
  *
 */
public class ProductDto {

	private String id;
	private String name;
	private String lotteryType;//彩票种类（1：体彩 2：福彩）
	
	private String lotteryTypeName;//彩票种类名称（1：体彩2：福彩）
	private String code;//产品编码
	private String provinceDm;//省份
	private String cityDm;//市
	private String price;//参考价格
	
	private String prourl;//软件产品的访问路径
	
	private String provinceName;//省份名称
	private String cityName;//市名称
	
	

	private String cpdlDm;//产品大类
	private String cpzlDm;//产品种类
	private String cpxlDm;//产品小类
	
	
	private String cpdlName;//产品大类名称
	private String cpzlName;//产品种类名称
	private String cpxlName;//产品小类名称
	
	private String createTime;//创建时间
	
	private String productDesprition;//产品描述
	
	private String sellPrice;//销售价格
	
	private String probation;//试用期
	
	private boolean isChecked;//是否为当前商品的对应产品
	
	private String durationOfUsername;//产品使用期名字
	
	private String durationOfuser;//产品使用期id
	
	
	
	
	

	public String getProurl() {
		return prourl;
	}

	public void setProurl(String prourl) {
		this.prourl = prourl;
	}

	public String getDurationOfuser() {
		return durationOfuser;
	}

	public void setDurationOfuser(String durationOfuser) {
		this.durationOfuser = durationOfuser;
	}

	public String getDurationOfUsername() {
		return durationOfUsername;
	}

	public void setDurationOfUsername(String durationOfUsername) {
		this.durationOfUsername = durationOfUsername;
	}

	public String getLotteryTypeName() {
		return lotteryTypeName;
	}

	public void setLotteryTypeName(String lotteryTypeName) {
		this.lotteryTypeName = lotteryTypeName;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
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

	public String getCpdlName() {
		return cpdlName;
	}

	public void setCpdlName(String cpdlName) {
		this.cpdlName = cpdlName;
	}

	public String getCpzlName() {
		return cpzlName;
	}

	public void setCpzlName(String cpzlName) {
		this.cpzlName = cpzlName;
	}

	public String getCpxlName() {
		return cpxlName;
	}

	public void setCpxlName(String cpxlName) {
		this.cpxlName = cpxlName;
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

	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProductDesprition() {
		return productDesprition;
	}

	public void setProductDesprition(String productDesprition) {
		this.productDesprition = productDesprition;
	}
	
	
}

package com.sdf.manager.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
  * @ClassName: resultBean 
  * @Description: TODO(返回数据bean) 
  * @author bann@sdfcp.com
  * @date 2015年10月9日 下午4:46:57 
  *
 */
public class ResultBeanDataList<T>
{
	private String message;//返回提示信息
	
	private String status;//返回状态（success of fail）
	
	private List<T> dataList = new ArrayList<T>();
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
}

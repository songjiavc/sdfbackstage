package com.sdf.manager.common.bean;


/**
 * 
  * @ClassName: resultBean 
  * @Description: TODO(返回数据bean) 
  * @author bann@sdfcp.com
  * @date 2015年10月9日 下午4:46:57 
  *
 */
public class ResultBean 
{
	private String message;//返回提示信息
	
	private String status;//返回状态（success of fail）
	
	private boolean isExist;//当前值是否存在

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

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	
	
}

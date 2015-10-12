package com.sdf.manager.user.bean;


/**
 * 
  * @ClassName: AuthorityBean 
  * @Description: TODO(用户存储权限的查询条件的bean) 
  * @author bann@sdfcp.com
  * @date 2015年10月10日 上午10:27:21 
  *
 */
public class AuthorityBean 
{
	private String code;
	
	private String authName;
	
	private String parentAuth;
	
	private String url;
	
	private String authImg;
	
	private String status;
	
	private int page;//当前页
	
	private int rows;//行数
	
	
	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getParentAuth() {
		return parentAuth;
	}

	public void setParentAuth(String parentAuth) {
		this.parentAuth = parentAuth;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthImg() {
		return authImg;
	}

	public void setAuthImg(String authImg) {
		this.authImg = authImg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}

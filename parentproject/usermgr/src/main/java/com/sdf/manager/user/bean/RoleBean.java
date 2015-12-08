package com.sdf.manager.user.bean;

/** 
  * @ClassName: RuleBean 
  * @Description: 角色的actionform
  * @author songj@sdfcp.com
  * @date 2015年10月19日 上午8:23:37 
  *  
  */
public class RoleBean 
{
	private String id;
	
	private String code;
	
	private String name;
	
	private String parentRolename;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getParentRolename() {
		return parentRolename;
	}

	public void setParentRolename(String parentRolename) {
		this.parentRolename = parentRolename;
	}

}
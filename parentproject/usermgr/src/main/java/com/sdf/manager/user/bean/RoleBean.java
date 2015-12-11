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
	
	/** 
	  * @Fields parentUid : 用户对应的上级角色id
	  */ 
	private String parentUid;

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

	public String getParentUid() {
		return parentUid;
	}

	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
	}
	
	

}
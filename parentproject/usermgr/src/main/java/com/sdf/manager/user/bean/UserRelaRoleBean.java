package com.sdf.manager.user.bean;

/** 
  * @ClassName: roleBean 
  * @Description: 角色的actionform
  * @author songj@sdfcp.com
  * @date 2015年10月19日 上午8:23:37 
  *  
  */
public class UserRelaRoleBean
{
	/** 
	  * @Fields userId : 用户id
	  */ 
	private String userId;
	
	/** 
	  * @Fields roleId : 角色id 
	  */ 
	private String roleId;
	
	/** 
	  * @Fields parentUid :  
	  */ 
	private String parentUid;
	
	private String roleCode;
	
	private String roleName;

	private String parentRolename;
	
	private String parentRole;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getParentUid() {
		return parentUid;
	}

	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getParentRolename() {
		return parentRolename;
	}

	public void setParentRolename(String parentRolename) {
		this.parentRolename = parentRolename;
	}

	public String getParentRole() {
		return parentRole;
	}

	public void setParentRole(String parentRole) {
		this.parentRole = parentRole;
	}
	
}

package com.sdf.manager.user.bean;

/** 
  * @ClassName: roleBean 
  * @Description: 角色的actionform
  * @author songj@sdfcp.com
  * @date 2015年10月19日 上午8:23:37 
  *  
  */
public class UserRleaRoleBean
{
	private String roleId;
	
	private String roleCode;
	
	private String roleName;

	public String getroleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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
}

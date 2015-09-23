package com.sdf.manager.user;

import java.util.List;

/**
 * 
* @ClassName: MenuBean 
* @Description: 导航菜单数据bean
* @author A18ccms a18ccms_gmail_com 
* @date 2015年9月22日 下午2:30:23 
*
 */
public class MenuBean 
{
	private String menuid;
	private String icon;
	private String menuname;
	private String url;
	private List<MenuBean> menus;
	
	
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<MenuBean> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuBean> menus) {
		this.menus = menus;
	} 
	
	
	
}

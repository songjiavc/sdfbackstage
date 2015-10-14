package com.sdf.manager.common.bean;


/**
 * 
  * @ClassName: TreeBean 
  * @Description: TODO(前台树展示bean类) 
  * @author bann@sdfcp.com
  * @date 2015年10月14日 下午2:56:23 
  *
 */
public class TreeBean {
	
	private String id;
	private String pId;//父级节点id，若为根节点则pId=0
	private String name;//当前节点显示名称
	private boolean open;//是否为打开，设置为true时打开
	private boolean isParent;//是否为父节点，设置为true时是父节点
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

}

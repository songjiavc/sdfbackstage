package com.sdf.manager.user.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Transient;

/** 
  * @ClassName: Student 
  * @Description: 获取
  * @author songj@sdfcp.com
  * @date 2015年9月23日 下午5:27:11 
  *  
  */
@Entity
@Table(name="T_SDF_ROLES")
public class Role extends BaseEntiry implements Serializable 
{
	
	
	/** 
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private static final long serialVersionUID = 7692132908491601627L;

	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="CODE",length=45)
	private String code;
	
	@Column(name="NAME",length=45)
	private String name;
	
	@Column(name="PARENT_ROLE",length=45)
	private String parentRole;
	
//	@Column(name="STATUS")
//	private String status;
	
	@Column(name="PARENT_ROLENAME")
	private String parentRolename;
	
	@Transient
	//表间关联的主控方的配置
	//@JoinTable描述了多对多关系的数据表关系。name属性指定中间表名称，joinColumns定义中间表与Role表的外键关系。
    //中间表RELA_SDF_AUTHORITY_ROLE的ROLE_ID列是Teacher表的主键列对应的外键列，inverseJoinColumns属性定义了中间表与另外一端(Authority)的外键关系。
    //属性referencedColumnName标注的是所关联表中的字段名，若不指定则使用的所关联表的主键字段名作为外键。 
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "RELA_SDF_AUTHORITY_ROLE", 
            joinColumns = {  @JoinColumn(name = "ROLE_ID", referencedColumnName = "id")  }, 
            inverseJoinColumns = {@JoinColumn(name = "authority_ID", referencedColumnName = "id") })
	private List<Authority> authorities ;

	
	
	
	public String getParentRolename() {
		return parentRolename;
	}

	public void setParentRolename(String parentRolename) {
		this.parentRolename = parentRolename;
	}


	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

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

	public String getParentRole() {
		return parentRole;
	}

	public void setParentRole(String parentRole) {
		this.parentRole = parentRole;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
}

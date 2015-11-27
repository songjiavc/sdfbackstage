package com.sdf.manager.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_SDF_AGENT_PROJECTS")
public class AgentProject implements Serializable 
{
	
	/** 
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	  */ 
	private static final long serialVersionUID = -6697649610333566777L;

	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	/** 
	  * @Fields projectCode : TODO(用一句话描述这个变量表示什么) 
	  */ 
	@Column(name="PROJECT_CODE")
	private String projectCode;
	/** 
	  * @Fields projectName : 代理项目名称
	  */ 
	@Column(name="PROJECT_NAME")
	private String projectName;
}

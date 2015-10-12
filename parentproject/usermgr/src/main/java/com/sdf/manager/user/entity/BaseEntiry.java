package com.sdf.manager.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
  * @ClassName: BaseEntiry 
  * @Description: TODO(这里用一句话描述这个类的作用) 
  * @author songj@sdfcp.com
  * @date 2015年9月23日 下午5:30:26 
  *  
  */
@MappedSuperclass
public class BaseEntiry {
//		@Id
//		protected String id;  
		  
		@Column(name="CREATER")
		protected String creater;
		
		@Column(name="CREATER_TIME")
		@Temporal(TemporalType.TIMESTAMP)
		protected Date createrTime;
		
		@Column(name="MODIFY")
		protected String 	modify;
		
		@Column(name="MODIFY_TIME")
		@Temporal(TemporalType.TIMESTAMP)
		protected Date modifyTime;

//		public String getId() {
//			return id;
//		}
//
//		public void setId(String id) {
//			this.id = id;
//		}

		public String getCreater() {
			return creater;
		}

		public void setCreater(String creater) {
			this.creater = creater;
		}

		
		public String getModify() {
			return modify;
		}

		public void setModify(String modify) {
			this.modify = modify;
		}

		public Date getCreaterTime() {
			return createrTime;
		}

		public void setCreaterTime(Date createrTime) {
			this.createrTime = createrTime;
		}

		public Date getModifyTime() {
			return modifyTime;
		}

		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}
}

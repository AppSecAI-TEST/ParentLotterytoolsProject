package com.BYL.lotteryTools.common.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
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
public class BaseEntity {

		@Column(name="CREATOR")
		protected String creator;
		
		@Column(name="CREATE_TIME")
		protected Timestamp createTime;
		
		@Column(name="MODIFY")
		protected String 	modify;
		
		@Column(name="MODIFY_TIME")
		protected Timestamp modifyTime;
		
		@Column(name="IS_DELETED")
		protected String 	isDeleted;
		

		
		public String getModify() {
			return modify;
		}

		public void setModify(String modify) {
			this.modify = modify;
		}

		


		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

		public Date getCreateTime() {
			return createTime;
		}


		public Date getModifyTime() {
			return modifyTime;
		}

		
		public void setCreateTime(Timestamp createTime) {
			this.createTime = createTime;
		}

		public void setModifyTime(Timestamp modifyTime) {
			this.modifyTime = modifyTime;
		}

		public String getIsDeleted() {
			return isDeleted;
		}

		public void setIsDeleted(String isDeleted) {
			this.isDeleted = isDeleted;
		}
		
}

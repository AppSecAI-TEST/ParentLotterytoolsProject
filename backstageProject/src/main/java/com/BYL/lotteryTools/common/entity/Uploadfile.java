package com.BYL.lotteryTools.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UPLOADFILE")
public class Uploadfile extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "newsUuid", length = 45)
	private String newsUuid;
	
	@Column(name = "uploadFileName", length = 200)
	private String uploadFileName;
	
	@Column(name = "uploadContentType", length = 20)
	private String uploadContentType;
	
	@Column(name = "uploadRealName", length = 50)
	private String uploadRealName;
	
	@Column(name = "uploadfilepath", length = 50)
	private String uploadfilepath;
	
	@Column(name = "des", length = 100)
	private String des;
	
	@Column(name = "deleteServiceFile")
	private boolean deleteServiceFile;



	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNewsUuid() {
		return this.newsUuid;
	}

	public void setNewsUuid(String newsUuid) {
		this.newsUuid = newsUuid;
	}
	
	
	

	public boolean isDeleteServiceFile() {
		return deleteServiceFile;
	}

	public void setDeleteServiceFile(boolean deleteServiceFile) {
		this.deleteServiceFile = deleteServiceFile;
	}

	
	public String getUploadFileName() {
		return this.uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	
	public String getUploadContentType() {
		return this.uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	
	public String getUploadRealName() {
		return this.uploadRealName;
	}

	public void setUploadRealName(String uploadRealName) {
		this.uploadRealName = uploadRealName;
	}

	
	public String getUploadfilepath() {
		return this.uploadfilepath;
	}

	public void setUploadfilepath(String uploadfilepath) {
		this.uploadfilepath = uploadfilepath;
	}

	
	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}

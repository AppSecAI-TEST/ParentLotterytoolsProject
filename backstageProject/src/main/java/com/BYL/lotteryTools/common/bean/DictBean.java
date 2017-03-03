package com.BYL.lotteryTools.common.bean;

/** 
  * @ClassName: DictBean 
  * @Description: 用于提供数据字典返回 
  *  
  */
public class DictBean
{
	/** 
	  * @Fields id : id  主键 
	  */ 
	private String id;
	
	/** 
	  * @Fields code : 编码 
	  */ 
	private String code;
	
	/** 
	  * @Fields name : 名称 
	  */ 
	private String name;

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
	
}

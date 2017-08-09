package com.BYL.lotteryTools.backstage.lotteryManage.service;

import java.util.Map;

/**
 * 彩种其他值计算
 * @author Administrator
 *
 */
public interface LotteryOtherNumCalService 
{
	/**
     * @Description: 计算11选5扩展表方法（12选5同）
     * @author songj@sdfcp.com
     * @date Feb 15, 2016 4:24:40 PM
     * @param issueId
     * @return
     *//*
	 public Map<String,Object> getRecordByIssueId(String issueId,String no1
			   ,String no2,String no3,String no4,String no5);
	 *//**
	    * 计算安徽快3扩展内容程序
	    * @param srcDataBean
	    * @return
	    *//*
	 public Map<String,Object> caluExtentInfo(String issueId,String no1
			   ,String no2,String no3);*/
	 
	 /**
	  * 计算安徽快三扩展内容，参数传值方式为map
	  * @param objects
	  * @return
	  */
	 public Map<String,Object> caluExtentInfo(String objects);
	 /**
	     * @Description: 计算11选5扩展表方法（12选5同）
	     * @author songj@sdfcp.com
	     * @date Feb 15, 2016 4:24:40 PM
	     * @param issueId
	     * @return
	     */
	 public Map<String,Object> getRecordByIssueId(String objects);
}

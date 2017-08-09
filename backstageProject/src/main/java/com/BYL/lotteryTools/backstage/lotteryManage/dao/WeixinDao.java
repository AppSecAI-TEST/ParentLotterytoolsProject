package com.BYL.lotteryTools.backstage.lotteryManage.dao;

import java.util.List;

public interface WeixinDao {

	public List findALL();
	
	public boolean checkIssueNumber(String tableName,String issueNum);
	
	public boolean addLpBuluPlan(String tableName, String issueNum, String numJ);
}

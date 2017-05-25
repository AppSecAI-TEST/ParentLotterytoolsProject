package com.BYL.lotteryTools.backstage.outer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryPlayService;
import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.outer.entity.SrcthreedataDTO;
import com.BYL.lotteryTools.backstage.outer.service.OuterInterfaceService;

/**
 * 推送数据源（定时任务判断是否有新的数据开出，将新数据按照tag_and推送到app）
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月14日 上午9:31:53
 */
@Component
public class PushOriginDataTask 
{
	private Logger logger = LoggerFactory.getLogger(PushOriginDataTask.class);
	
	@Autowired
	private LotteryPlayService lotteryPlayService;
	
	@Autowired
	private OuterInterfaceService outerInterfaceService;
	
	private List<LotteryPlay> lotteryPlays;//当前彩种列表
	
	private Map<String,String> issueMap = new HashMap<String, String>();//最大期号map
	
	//需要注意@Scheduled这个注解，它可配置多个属性：cron\fixedDelay\fixedRate  
	public void initLotteryPlays() 
	{  
		lotteryPlays = new ArrayList<LotteryPlay>();
		lotteryPlays = lotteryPlayService.getAllLotteryPlays();
		
 	}
	
	

	//判断当前维护的所有高频彩种是否开出新一期，开出后进行推送
	@Scheduled(cron = "0/30 * * * * ? ") //30s执行1次
    //需要注意@Scheduled这个注解，它可配置多个属性：cron\fixedDelay\fixedRate  
    public void tuisongMethod() 
 	{ 
        if(null!= lotteryPlays &&lotteryPlays.size()>0)
        {
        	for (LotteryPlay lotteryPlay : lotteryPlays) 
        	{
        		StringBuffer buffer = new StringBuffer(lotteryPlay.getProvince());
        		buffer.append(lotteryPlay.getLotteryType()).append(lotteryPlay.getCode());
				try
				{//判断当前彩种是否开奖
					SrcfivedataDTO maxdto =  null;//开奖号码为5个号码的彩种实体
					SrcthreedataDTO maxThreedto = null;//开奖号码为3个号码的彩种实体
					boolean tuisongFlag= true;
					if(null !=issueMap.get(buffer.toString()))
					{//当前期号组合中有最大期号,获取比当前最大期号大的数据
						if("5".equals(lotteryPlay.getLotteryNumber()))
						{
							 maxdto = outerInterfaceService.getMaxLottery
										(lotteryPlay.getCorrespondingTable(), issueMap.get(buffer.toString()));
								 if(null == maxdto )
								 {//当前最大期号就是最大的数据
									 tuisongFlag = false;
								 }
								 else
								 {
									 issueMap.put(buffer.toString(), maxdto.getIssueNumber());//存储最大期号
								 }
						}
						else
							if("3".equals(lotteryPlay.getLotteryNumber()))
							{
								 maxThreedto = outerInterfaceService.getMaxThreeLottery
											(lotteryPlay.getCorrespondingTable(), issueMap.get(buffer.toString()));
								 if(null == maxThreedto )
								 {//当前最大期号就是最大的数据
									 tuisongFlag = false;
								 }
								 else
								 {
									 issueMap.put(buffer.toString(), maxThreedto.getIssueNumber());//存储最大期号
								 }
							}
						
					}
					else
					{
						if("5".equals(lotteryPlay.getLotteryNumber()))
						{
							 maxdto = outerInterfaceService.getMaxLottery
										(lotteryPlay.getCorrespondingTable(), null);
								issueMap.put(buffer.toString(), maxdto.getIssueNumber());//存储最大期号
						}
						else
							if("3".equals(lotteryPlay.getLotteryNumber()))
							{
								maxThreedto = outerInterfaceService.getMaxThreeLottery
										(lotteryPlay.getCorrespondingTable(), null);
								issueMap.put(buffer.toString(), maxThreedto.getIssueNumber());//存储最大期号
							}
						
						
					}
					//将推送内容进行推送
					if(tuisongFlag)
					{
						String extra = "推送内容类型";
						String[] tagsand = {lotteryPlay.getProvince(),lotteryPlay.getLotteryType()};
						StringBuffer msgContent = new StringBuffer();
						if("5".equals(lotteryPlay.getLotteryNumber()))
						{
							msgContent.append(maxdto.getIssueNumber()).append(",");
							msgContent.append(maxdto.getNo1()).append(",");
							msgContent.append(maxdto.getNo2()).append(",");
							msgContent.append(maxdto.getNo3()).append(",");
							msgContent.append(maxdto.getNo4()).append(",");
							msgContent.append(maxdto.getNo5());
							String endNumber = lotteryPlay.getLotteryPlayBulufangan().getEndNumber();
							if("11".equals(endNumber))
							{
								extra = "5in11";
							}
							else
								if("12".equals(endNumber))
								{
									extra = "5in12";
								}
						}
						else
							if("3".equals(lotteryPlay.getLotteryNumber()))
							{
								msgContent.append(maxThreedto.getIssueNumber()).append(",");
								msgContent.append(maxThreedto.getNo1()).append(",");
								msgContent.append(maxThreedto.getNo2()).append(",");
								msgContent.append(maxThreedto.getNo3());
								extra = "kuai3";
							}
						//extra:0:
						PushController.sendPushWithCallback(tagsand, null, msgContent.toString(), extra);
					}
					
				}
				catch(Exception e)
				{
					logger.error("彩种报错："+lotteryPlay.getName());
					logger.error("message:", e);
				}
			}
        }
        else
        {
        	this.initLotteryPlays();
        }
    }  
}

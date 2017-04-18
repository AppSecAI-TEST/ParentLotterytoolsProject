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
	
	@Scheduled(cron = "0 0/10 12 * * ? ") //10min执行1次
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
					SrcfivedataDTO maxdto =  null;
					boolean tuisongFlag= true;
					System.out.println(buffer+"="+issueMap.get(buffer.toString()));
					if(null !=issueMap.get(buffer.toString()))
					{//当前期号组合中有最大期号,获取比当前最大期号大的数据
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
					{
						 maxdto = outerInterfaceService.getMaxLottery
								(lotteryPlay.getCorrespondingTable(), null);
						issueMap.put(buffer.toString(), maxdto.getIssueNumber());//存储最大期号
						
					}
					//将推送内容进行推送
					if(tuisongFlag)
					{
						String[] tagsand = {lotteryPlay.getProvince(),lotteryPlay.getLotteryType()};
						StringBuffer msgContent = new StringBuffer();
						msgContent.append(maxdto.getIssueNumber()).append(",");
						msgContent.append(maxdto.getNo1()).append(",");
						msgContent.append(maxdto.getNo2()).append(",");
						msgContent.append(maxdto.getNo3()).append(",");
						msgContent.append(maxdto.getNo4()).append(",");
						msgContent.append(maxdto.getNo5());
						PushController.sendPushWithCallback(tagsand, null, msgContent.toString(), lotteryPlay.getProvince());
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

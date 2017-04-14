package com.BYL.lotteryTools.backstage.outer.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 推送数据源（定时任务判断是否有新的数据开出，将新数据按照tag_and推送到app）
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月14日 上午9:31:53
 */
@Component
public class PushOriginDataTask 
{

//	@Scheduled(cron = "0/30 * * * * ? ") //30s执行1次
//    //需要注意@Scheduled这个注解，它可配置多个属性：cron\fixedDelay\fixedRate  
//    public void test() 
// 	{  
//        System.out.println("testjob");  
//    }  
}

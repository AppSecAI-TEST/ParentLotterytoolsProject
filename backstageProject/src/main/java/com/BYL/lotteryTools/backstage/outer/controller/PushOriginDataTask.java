package com.BYL.lotteryTools.backstage.outer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

import com.BYL.lotteryTools.backstage.lotteryGroup.controller.OuterLotteryGroupController;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryPlayService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.controller.OuterLotteryBuyerOrExpertController;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.outer.entity.SrcthreedataDTO;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.service.OuterInterfaceService;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.exception.GlobalExceptionHandler;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 * 推送数据源（定时任务判断是否有新的数据开出，将新数据按照tag_and推送到app）
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月14日 上午9:31:53
 */
@Component
public class PushOriginDataTask extends GlobalExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(PushOriginDataTask.class);
	
	@Autowired
	private LotteryPlayService lotteryPlayService;
	
	@Autowired
	private OuterInterfaceService outerInterfaceService;
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@Autowired
	private RongyunImService rongyunImService;
	
	
	@Autowired
	private LotteryGroupService lotteryGroupService;
	
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
						//1.推送开奖号
						String extra = "推送内容类型";
						//Add by banna in 2017/6/6 推送tag多加一个开奖号码个数tag，用来区分同一彩种类型的不同彩种，例如辽宁12选5和辽宁快三
						String[] tagsand = {lotteryPlay.getProvince(),lotteryPlay.getLotteryType(),lotteryPlay.getLotteryNumber()};
						StringBuffer msgContent = new StringBuffer();
						StringBuffer imgContent = new StringBuffer();//图片内容content
						String imgFile = "";//走势图缩略图
						StringBuffer issueNumContent = new StringBuffer();//期号内容
						if("5".equals(lotteryPlay.getLotteryNumber()))
						{
							issueNumContent.append(lotteryPlay.getName()+" ").
											append(maxdto.getIssueNumber()+"开奖号码:").
											append(maxdto.getNo1()+",").
											append(maxdto.getNo2()+",").
											append(maxdto.getNo3()+",").
											append(maxdto.getNo4()+",").
											append(maxdto.getNo5());
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
								//TODO:拼接图片大小
//								imgFile = "d://5in11ImgBase.jpg";//走势图缩略图
								imgFile = "/home/server/webappProject/webapps/webappProject/images/5in11ImgBase.jpg";//服务器版本
								imgContent.append(OuterLotteryBuyerOrExpertController.DOMAIN+"/webappProject/images/5in11Img.png");//拼接走势图图片
							}
							else
								if("12".equals(endNumber))
								{
									extra = "5in12";
//									imgFile = "d://5in11ImgBase.jpg";//走势图缩略图
									imgFile = "/home/server/webappProject/webapps/webappProject/images/5in11ImgBase.jpg";//服务器版本
									imgContent.append(OuterLotteryBuyerOrExpertController.DOMAIN+"/webappProject/images/5in11Img.png");//拼接走势图图片
								}
						}
						else
							if("3".equals(lotteryPlay.getLotteryNumber()))
							{
								issueNumContent.append(lotteryPlay.getName()+" ").
								append(maxdto.getIssueNumber()+"开奖号码:").
								append(maxdto.getNo1()+",").
								append(maxdto.getNo2()+",").
								append(maxdto.getNo3()+",");
								
								msgContent.append(maxThreedto.getIssueNumber()).append(",");
								msgContent.append(maxThreedto.getNo1()).append(",");
								msgContent.append(maxThreedto.getNo2()).append(",");
								msgContent.append(maxThreedto.getNo3());
								extra = "kuai3";
							}
						//extra:0:
						PushController.sendPushWithCallback(tagsand, null, msgContent.toString(), extra);
						
						
						//2.向群发送开奖消息
						//获取当前省份的机器人数据
						String flag = this.sendingToGroup(lotteryPlay, imgContent.toString(), issueNumContent.toString(),imgFile);
						LOG.info("机器人推送:"+flag);
					}
					
				}
				catch(Exception e)
				{
					LOG.error("彩种报错："+lotteryPlay.getName());
					LOG.error("message:", e);
				}
			}
        }
        else
        {
        	this.initLotteryPlays();
        }
    }  
	
	/**
	 * 
	* @Title: sendMessageToGroups 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotteryPlay
	* @param @param imgContent:图片内容
	* @param @param issueNumContent：开奖号内容
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月27日 上午10:22:47 
	* @return boolean    返回类型 
	* @throws
	 */
	/*public boolean sendMessageToGroups(LotteryPlay lotteryPlay,String imgContent,String issueNumContent) throws Exception
	{
		boolean flag = true;
		QueryResult<LotterybuyerOrExpert> robotResult = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertList(1, Integer.MAX_VALUE, null, lotteryPlay.getProvince(), null, "1",null);
		List<LotterybuyerOrExpert> robotList = robotResult.getResultList();
		String message = "";
		for (LotterybuyerOrExpert robot : robotList) {
			//获取当前机器人的群列表
			List<LotteryGroup> groups = lotteryGroupService.getLotteryGroupByGroupRobotID(robot.getId());
			message = this.sendingToGroupByRobot(robot.getId(), groups, imgContent, issueNumContent); 
			LOG.info(robot.getName()+"发送status:",message);
		}
		
		return flag;
	}*/
	
	//由机器人向群发送开奖消息
	public String sendingToGroup(LotteryPlay lotteryPlay,String imgContent,String issueNumContent,String imgFile) 
			throws Exception
	{
		List<LotteryGroup> groups = lotteryGroupService.
				getLotteryGroupByProvinceAndLotteryType(lotteryPlay.getProvince(), lotteryPlay.getLotteryType());
		String message = "";
		int timerCount = 0;//20条消息计时器
		for (LotteryGroup lotteryGroup : groups) {
			message = "pushToGroup";
			//发布开奖
			String[] group = {lotteryGroup.getId()};
			if(1 == lotteryGroup.getFabuKj())
			{
				CodeSuccessResult result =  rongyunImService.sendMessgeToGroups
						(lotteryGroup.getGroupRobotID(), group, issueNumContent);
				if(OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
				{
					LOG.info("success");
					timerCount++;
				}
			}
			//发布走势
			if(1 == lotteryGroup.getFabuZs())
			{
//				rongyunImService.sendMessgeToGroups(lotteryGroup.getGroupRobotID(), group, imgContent);//imgContent
		        InputStream in = null;  
		        byte[] data = null;  
		        //读取图片字节数组  
		        try   
		        {  
		        	File file = new File(imgFile);
		            in = new FileInputStream(file);          
		            data = new byte[in.available()];  
		            in.read(data);  
		            in.close();  
		        }   
		        catch (IOException e)   
		        {  
		            e.printStackTrace();  
		        }  
		        //对字节数组Base64编码  
		        BASE64Encoder encoder = new BASE64Encoder();  
		        //返回Base64编码过的字节数组字符串  
		        StringBuffer type = new StringBuffer("zoushi");
		        type.append(",").append(lotteryPlay.getId()).
		        	 append(",").append(lotteryPlay.getLotteryType()).
		        	 append(",").append(lotteryPlay.getProvince()).
		        	 append(",").append(lotteryPlay.getLotteryNumber());
		        CodeSuccessResult result =  rongyunImService.sendImgMessgeToGroups(lotteryGroup.getGroupRobotID(), 
						group, encoder.encode(data).replace("\r\n", "").replace("\r", "").replace("\n", "") ,
						imgContent,type.toString());//图片内容的extra的值为"zoushi"
		        if(OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
				{
					LOG.info("success");
					timerCount++;
				}
			}
			
			if(20 == timerCount)
			{
				Thread.sleep(1000);//延时1s
				LOG.info("waiting......................");
				timerCount = 0;//延时后重置计时器
			}
		}
		
		return message;
	}
	
}

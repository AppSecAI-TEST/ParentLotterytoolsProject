package com.BYL.lotteryTools.backstage.lotteryStation.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryStation.dto.LotteryStationDTO;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotteryStation.service.LotteryStationService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.exception.GlobalOuterExceptionHandler;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;

/**
 * 
* @Description: TODO(外部接口彩票站控制类) 
* @author banna
* @date 2017年3月14日 上午9:53:09
 */
@Controller
@RequestMapping("/outerLotteryStation")
public class OuterLotteryStationController extends GlobalOuterExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(OuterLotteryStationController.class);
	
	@Autowired
	private LotteryStationService lotteryStationService;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@Autowired
	private RongyunImService rongyunImService;
	
	
	
	/*@RequestMapping(value = "/submitFromAppLS", method = RequestMethod.POST)
	public @ResponseBody ResultBean submitFromAppLS(
				LotteryStationDTO lotteryStationDTO,HttpServletRequest request,HttpSession httpSession)
	{
		ResultBean resultBean  = new ResultBean();
		
		if(null != lotteryStationDTO.getDaixiaoImgFile())
		{
			Uploadfile uploadfile =null;
			String newsUuid = UUID.randomUUID().toString();
			try {
					 uploadfile = uploadfileService.uploadFiles(lotteryStationDTO.getDaixiaoImgFile(),request,newsUuid);
				
			} catch (Exception e) {
				LOG.error("error:", e);
			}
			
			System.out.println("id="+lotteryStationDTO.getId());
		}
		
		return resultBean;
	}*/
	/**
	 * 保存用户在app端输入的彩票站信息
	* @Title: saveFromAppLotteryStaion 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotteryStationDTO
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月19日 上午11:12:58 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/submitFromAppLotteryStaion", method = RequestMethod.POST)
	public @ResponseBody ResultBean submitFromAppLotteryStaion(
				LotteryStationDTO lotteryStationDTO,HttpServletRequest request,HttpSession httpSession)
	{
		ResultBean resultBean  = new ResultBean();
		try
		{
			//1.判断当前彩票站是否已经认证过
			LotteryStation lotteryStation = lotteryStationService.
					getLotteryStationByStationNumber(lotteryStationDTO.getStationNumber());
			
			//获取彩票站站主信息
			LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
					getLotterybuyerOrExpertById(lotteryStationDTO.getUserId());
			
			if(null == lotteryStation)
			{
				/*String sessionId = OuterLotteryBuyerOrExpertController.sessionMap.get(lotteryStationDTO.getTelephone());
				SMSVerifyCodeResult yanzhengma = null;
				if(null != sessionId)
				{
					 yanzhengma = rongyunImService.verifyCode(sessionId, lotteryStationDTO.getYanzhengma());
				}
				
				if(yanzhengma.getSuccess())
				{*/
					//2.新建彩票站信息
					lotteryStation = new LotteryStation();
					BeanUtil.copyBeanProperties(lotteryStation, lotteryStationDTO);
					
					lotteryStation.setFromApp("1");//app提交认证的彩票站信息
					lotteryStation.setApprovalStatus("0");//审核中
					lotteryStation.setCreateTime(new Timestamp(System.currentTimeMillis()));
					lotteryStation.setCreator(lotteryStation.getId());
					lotteryStation.setModifyTime(new Timestamp(System.currentTimeMillis()));
					lotteryStation.setModify(lotteryStation.getId());
					lotteryStation.setIsDeleted(Constants.IS_NOT_DELETED);
					
					//关联彩票站的营业执照图片文件
					/*if(null != lotteryStationDTO.getDaixiaoImg()&&!"".equals(lotteryStationDTO.getDaixiaoImg()))
					{
						lotteryStation.setDaixiaoImg(lotteryStationDTO.getDaixiaoImg());
					}*/
					if(null != lotteryStationDTO.getDaixiaoImgFile())
					{
						Uploadfile uploadfile =null;
						String newsUuid = UUID.randomUUID().toString();
						try {
								 uploadfile = uploadfileService.uploadFiles(lotteryStationDTO.getDaixiaoImgFile(),request,newsUuid);
							
						} catch (Exception e) {
							LOG.error("error:", e);
						}
						if(null != uploadfile)
							lotteryStation.setDaixiaoImg(uploadfile.getNewsUuid());
					}
					
					//关联身份证图片调用方法
					/*if(null != lotteryStationDTO.getIdNumberFrontImg()&&!"".equals(lotteryStationDTO.getIdNumberFrontImg()))
					{
						lotteryStation.setIdNumberFrontImg(lotteryStationDTO.getIdNumberFrontImg());
					}*/
					if(null != lotteryStationDTO.getIdNumberFrontImgFile())
					{
						Uploadfile uploadfile =null;
						String newsUuid = UUID.randomUUID().toString();
						try {
								 uploadfile = uploadfileService.uploadFiles(lotteryStationDTO.getIdNumberFrontImgFile(),request,newsUuid);
							
						} catch (Exception e) {
							LOG.error("error:", e);
						}
						if(null != uploadfile)
							lotteryStation.setIdNumberFrontImg(uploadfile.getNewsUuid());
					}
					/*if(null != lotteryStationDTO.getIdNumberBackImg()&&!"".equals(lotteryStationDTO.getIdNumberBackImg()))
					{
						lotteryStation.setIdNumberBackImg(lotteryStationDTO.getIdNumberBackImg());
					}*/
					if(null != lotteryStationDTO.getIdNumberBackImgFile())
					{
						Uploadfile uploadfile =null;
						String newsUuid = UUID.randomUUID().toString();
						try {
								 uploadfile = uploadfileService.uploadFiles(lotteryStationDTO.getIdNumberBackImgFile(),request,newsUuid);
							
						} catch (Exception e) {
							LOG.error("error:", e);
						}
						if(null != uploadfile)
							lotteryStation.setIdNumberBackImg(uploadfile.getNewsUuid());
					}
					
					//将站主和彩票站关联
					lotteryStation.setLotteryBuyerOrExpert(lotterybuyerOrExpert);
					//保存彩票站信息
					lotteryStationService.save(lotteryStation);
					resultBean.setFlag(true);
					resultBean.setStatus("success");
					resultBean.setMessage("提交成功");
				/*}
				else
				{//手机验证码验证失败
					resultBean.setStatus("fail");
					resultBean.setMessage(yanzhengma.getErrorMessage());
				}*/
				
			}
			else
			{
				resultBean.setExist(true);
				resultBean.setFlag(true);
				resultBean.setStatus("error");
				resultBean.setMessage("当前彩票站已认证");
			}
			
		}
		catch(Exception e)
		{
			LOG.error("error:", e);
			resultBean.setFlag(false);
			resultBean.setMessage("服务器错误");
		}
		
		
		return resultBean;
	}
	
	/**
	 * 获取当前用户认证过的彩票站列表
	* @Title: getLotteryStaionOfRenzheng 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotteryStationDTO
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月21日 下午2:35:05 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getLotteryStaionOfRenzheng", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getLotteryStaionOfRenzheng(
				LotteryStationDTO lotteryStationDTO,HttpServletRequest request,HttpSession httpSession)
	{
		 Map<String,Object> map = new HashMap<String, Object>();
		 
		 List<LotteryStation> lotteryStations = lotteryStationService.getLotteryStationByUserId(lotteryStationDTO.getUserId());
		 
		 List<LotteryStationDTO> dtos = lotteryStationService.toDTOs(lotteryStations);
		 
		 map.put("flag", true);
		 map.put("message", "获取成功");
		 map.put("stations", dtos);
		 
		 return map;
	}
}

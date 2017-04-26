package com.BYL.lotteryTools.backstage.lotteryStation.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;

/**
 * 
* @Description: TODO(外部接口彩票站控制类) 
* @author banna
* @date 2017年3月14日 上午9:53:09
 */
@Controller
@RequestMapping("/outerLotteryStation")
public class OuterLotteryStationController
{
	private Logger logger = LoggerFactory.getLogger(OuterLotteryStationController.class);
	
	@Autowired
	private LotteryStationService lotteryStationService;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
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
	@RequestMapping(value = "/submitFromAppLotteryStaion", method = RequestMethod.GET)
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
				//2.新建彩票站信息
				lotteryStation = new LotteryStation();
				BeanUtil.copyBeanProperties(lotteryStation, lotteryStationDTO);
				
				lotteryStation.setFromApp("1");//app提交认证的彩票站信息
				lotteryStation.setApprovalStatus("0");//审核中
				lotteryStation.setCreateTime(new Timestamp(System.currentTimeMillis()));
				lotteryStation.setCreator(LoginUtils.getAuthenticatedUserCode(httpSession));
				lotteryStation.setModifyTime(new Timestamp(System.currentTimeMillis()));
				lotteryStation.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				lotteryStation.setIsDeleted(Constants.IS_NOT_DELETED);
				
				//关联彩票站的营业执照图片文件
				if(null != lotteryStationDTO.getDaixiaoImg()&&!"".equals(lotteryStationDTO.getDaixiaoImg()))
				{
					lotteryStation.setDaixiaoImg(lotteryStationDTO.getDaixiaoImg());
				}
				
				//关联身份证图片调用方法
				
				if(null != lotteryStationDTO.getIdNumberFrontImg()&&!"".equals(lotteryStationDTO.getIdNumberFrontImg()))
				{
					lotterybuyerOrExpert.setIdNumberFrontImg(lotteryStationDTO.getIdNumberFrontImg());
				}
				
				if(null != lotteryStationDTO.getIdNumberBackImg()&&!"".equals(lotteryStationDTO.getIdNumberBackImg()))
				{
					lotterybuyerOrExpert.setIdNumberBackImg(lotteryStationDTO.getIdNumberBackImg());
				}
				//保存站主信息
				lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
				
				//将站主和彩票站关联
				lotteryStation.setLotteryBuyerOrExpert(lotterybuyerOrExpert);
				//保存彩票站信息
				lotteryStationService.save(lotteryStation);
				resultBean.setStatus("success");
				resultBean.setMessage("提交成功");
			}
			else
			{
				resultBean.setExist(true);
				resultBean.setStatus("error");
				resultBean.setMessage("当前彩票站已认证");
			}
			
		}
		catch(Exception e)
		{
			logger.error("error:", e);
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

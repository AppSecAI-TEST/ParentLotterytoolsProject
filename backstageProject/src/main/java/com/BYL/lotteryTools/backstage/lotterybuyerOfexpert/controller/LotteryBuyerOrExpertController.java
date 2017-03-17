package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.util.LoginUtils;

/**
 * 
* @Description: TODO(用户（彩民/专家）控制类) 
* @author banna
* @date 2017年3月14日 上午9:52:33
 */
@Controller
@RequestMapping("/lbuyerOrexpert")
public class LotteryBuyerOrExpertController 
{
	private Logger logger = LoggerFactory.getLogger(LotteryBuyerOrExpertController.class);
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@RequestMapping(value="/getDetailLborExpert" , method = RequestMethod.GET)
	public @ResponseBody LotterybuyerOrExpertDTO getDetailLborExpert(
			@RequestParam(value="id",required=true) String id)
	{
		LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO = new LotterybuyerOrExpertDTO();
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(id);
		
		lotterybuyerOrExpertDTO = lotterybuyerOrExpertService.toDTO(lotterybuyerOrExpert);
		
		return lotterybuyerOrExpertDTO;
	}
	
	
	
	
	
	/**
	 * 
	* @Title: deleteLborExpert 
	* @Description: 删除彩民数据
	* @param @param ids
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月17日 下午3:23:51 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteLborExpert", method = RequestMethod.POST)
	public @ResponseBody ResultBean  deleteLborExpert(
			@RequestParam(value="ids",required=false) String[] ids,
			ModelMap model,HttpSession httpSession) throws Exception 
	{
	 
		 ResultBean resultBean = new ResultBean();
		 
		 LotterybuyerOrExpert lotterybuyerOrExpert;
		 for (String id : ids) 
			{
			 	lotterybuyerOrExpert = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(id);
			 	if(null != lotterybuyerOrExpert)
			 	{
			 		lotterybuyerOrExpert.setIsDeleted("0");
			 		lotterybuyerOrExpert.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 		lotterybuyerOrExpert.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 		
			 		
			 		lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
			 		
			 		 //日志输出
					 logger.info("删除彩民数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				   
			 	}
			}
		 String returnMsg = "删除成功!";
		 resultBean.setStatus("success");
		 resultBean.setMessage(returnMsg);
		 
		 return resultBean;
			 
	 
	}
	
	
}

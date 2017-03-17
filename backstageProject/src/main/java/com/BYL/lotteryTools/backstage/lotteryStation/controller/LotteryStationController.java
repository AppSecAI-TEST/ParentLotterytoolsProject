package com.BYL.lotteryTools.backstage.lotteryStation.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryStation.dto.LotteryStationDTO;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotteryStation.service.LotteryStationService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 * 
* @Description: TODO(彩票站控制类) 
* @author banna
* @date 2017年3月14日 上午9:53:09
 */
@Controller
@RequestMapping("/lotteryStation")
public class LotteryStationController
{
	private Logger logger = LoggerFactory.getLogger(LotteryStationController.class);
	
	@Autowired
	private LotteryStationService lotteryStationService;
	
	/**
	 * 
	* @Title: getDetailStation 
	* @Description: TODO(获取彩票站数据详情) 
	* @param @param id
	* @param @return    设定文件 
	* @author banna
	* @date 2017年3月14日 上午9:25:10 
	* @return LotteryStationDTO    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getDetailStation" , method = RequestMethod.GET)
	public @ResponseBody LotteryStationDTO getDetailStation(
			@RequestParam(value="id",required=true) String id)
	{
		LotteryStationDTO lotteryStationDTO = new LotteryStationDTO();
		
		LotteryStation lotteryStation = lotteryStationService.getLotteryStationById(id);
		
		lotteryStationDTO = lotteryStationService.toDTO(lotteryStation);
		
		return lotteryStationDTO;
	}
	
	/**
	 * 
	* @Title: getLotteryStationList 
	* @Description: TODO(获取彩票站列表) 
	* @param @param page
	* @param @param rows
	* @param @param name
	* @param @param provinceCode
	* @param @param lotteryType
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月14日 上午10:32:37 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getLotteryStationList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getLotteryStationList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="provinceCode",required=false) String provinceCode,
			@RequestParam(value="lotteryType",required=false) String lotteryType,//彩种，体彩/福彩
			ModelMap model,HttpSession httpSession) throws Exception
	{
	 	Map<String,Object> returnData = new HashMap<String, Object>();
	 	
	 	/**补录的表名处理：之后实在没办法就直接写语句执行sql吧，插入补录数据**/
		
	 	//放置分页参数
		Pageable pageable = new PageRequest(page-1,rows);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		//连接查询条件
		if(null != name&&!"".equals(name.trim()))
		{
			params.add("%"+name+"%");
			buffer.append(" and name like ?").append(params.size());
		}
		
		if(null != provinceCode && !"".equals(provinceCode)&& !Constants.PROVINCE_ALL.equals(provinceCode))
		{
			params.add(provinceCode);
			buffer.append(" and province = ?").append(params.size());
		}
		
		if(null != lotteryType && !"".equals(lotteryType))
		{
			params.add(lotteryType);
			buffer.append(" and lotteryType = ?").append(params.size());
		}
		
		
	 	
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<LotteryStation> lQueryResult = lotteryStationService
				.getLotteryStationList(LotteryStation.class,
				buffer.toString(), params.toArray(),orderBy, pageable);
				
		List<LotteryStation> list = lQueryResult.getResultList();
		Long totalrow = lQueryResult.getTotalRecord();
		
		//将实体转换为dto
		List<LotteryStationDTO> lotteryStationDTOs = lotteryStationService.toDTOs(list);
		
		returnData.put("rows", lotteryStationDTOs);
		returnData.put("total", totalrow);
	 	
	 	
	 	return returnData;
	}
	
	
	@RequestMapping(value = "/saveOrUpdateLotteryStaion", method = RequestMethod.GET)
	public @ResponseBody ResultBean saveOrUpdateLotteryStaion(
			LotteryStationDTO lotteryStationDTO,
			/*@RequestParam(value="id",required=false) String id,
			@RequestParam(value="stationOwner",required=false) String stationOwner,
			@RequestParam(value="telephone",required=false) String telephone,
			@RequestParam(value="stationNumber",required=false) String stationNumber,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="password",required=false) String password,
			@RequestParam(value="province",required=false) String province,
			@RequestParam(value="city",required=false) String city,
			@RequestParam(value="country",required=false) String country,
			@RequestParam(value="coordinate",required=false) String coordinate,
			@RequestParam(value="address",required=false) String address,
			@RequestParam(value="stationInterview",required=false) String stationInterview,
			@RequestParam(value="openDoorTimeStr",required=false) String openDoorTimeStr,
			@RequestParam(value="closeDoorTime",required=false) String closeDoorTime,*/
			ModelMap model,HttpSession httpSession) throws Exception
	{
	   ResultBean resultBean = new ResultBean ();
	   
	   LotteryStation lotteryStation = lotteryStationService.getLotteryStationById(lotteryStationDTO.getId());
	   
	   
	   if(null != lotteryStation)
	   {
		   
		                 
		   
		   logger.info("修改彩票站--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
		   
		   resultBean.setMessage("添加彩票站数据成功!");
		   resultBean.setStatus("success");
	   }
	   else
	   {
		   lotteryStation = new LotteryStation();
		   
		   logger.info("添加彩票站--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
		   
		   resultBean.setMessage("添加彩票站数据成功!");
		   resultBean.setStatus("success");
		   
		   
	   }
	   
	   
	   return resultBean;
	
	
	
	}
	
	/**
	 * 
	* @Title: deleteLotteryStations 
	* @Description: TODO(删除彩票站数据) 
	* @param @param ids
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月14日 上午10:35:59 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteLotteryStations", method = RequestMethod.POST)
	public @ResponseBody ResultBean  deleteLotteryStations(
			@RequestParam(value="ids",required=false) String[] ids,
			ModelMap model,HttpSession httpSession) throws Exception 
	{
	 
		 ResultBean resultBean = new ResultBean();
		 
		 LotteryStation lotteryStation;
		 for (String id : ids) 
			{
			 	lotteryStation	 = lotteryStationService.getLotteryStationById(id);
			 	if(null != lotteryStation)
			 	{
			 		lotteryStation.setIsDeleted("0");
			 		lotteryStation.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 		lotteryStation.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 		
			 		
			 		lotteryStationService.update(lotteryStation);
			 		
			 		 //日志输出
					 logger.info("删除彩票站数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				   
			 	}
			}
		 String returnMsg = "删除成功!";
		 resultBean.setStatus("success");
		 resultBean.setMessage(returnMsg);
		 
		 return resultBean;
			 
	 
	}
	
	
}

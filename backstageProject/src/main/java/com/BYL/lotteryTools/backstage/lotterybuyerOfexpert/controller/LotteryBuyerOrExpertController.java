package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.BYL.lotteryTools.backstage.lotteryStation.dto.LotteryStationDTO;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.QueryResult;

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
	
	@Autowired
	private RongyunImService rongyunImService;//融云service层
	
	@Autowired
	private UploadfileService uploadfileService;
	
	/**
	 * 获取用户详情
	* @Title: getDetailLborExpert 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月19日 下午3:17:05 
	* @return LotterybuyerOrExpertDTO    返回类型 
	* @throws
	 */
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
	 * 获取用户列表
	* @Title: getLborExpertList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param page
	* @param @param rows
	* @param @param name
	* @param @param provinceCode
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年4月19日 下午3:17:58 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getLborExpertList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getLborExpertList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="provinceCode",required=false) String provinceCode,
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
		
		
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<LotterybuyerOrExpert> lQueryResult = lotterybuyerOrExpertService
				.getLotterybuyerOrExpertList(LotterybuyerOrExpert.class,
				buffer.toString(), params.toArray(),orderBy, pageable);
				
		List<LotterybuyerOrExpert> list = lQueryResult.getResultList();
		Long totalrow = lQueryResult.getTotalRecord();
		
		//将实体转换为dto
		List<LotterybuyerOrExpertDTO> dtos = lotterybuyerOrExpertService.toDTOs(list);
		
		returnData.put("rows", dtos);
		returnData.put("total", totalrow);
	 	
	 	
	 	return returnData;
	}
	
	
	/**
	 * 获取验证码
	* @Title: getYanzhengma 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月18日 下午5:10:32 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getYanzhengma" , method = RequestMethod.GET)
	public void getYanzhengma()
	{
		//TODO:调用第三方api给用户发送信息
		
	}
	
	/**
	 * 保存或修改来自前端App的信息
	* @Title: saveFromApp 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月18日 下午5:20:11 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/saveFromApp" , method = RequestMethod.GET)
	public @ResponseBody ResultBean saveFromApp(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request)
	{
		ResultBean resultBean = new ResultBean();
		//app端传的保存参数中，图片是file类型的文件，而后台是存储之后的图片id
		LotterybuyerOrExpert lotterybuyerOrExpert = new LotterybuyerOrExpert();
		try
		{
			BeanUtil.copyBeanProperties(lotterybuyerOrExpert, lotterybuyerOrExpertDTO);
			lotterybuyerOrExpert.setId(UUID.randomUUID().toString());
			//上传身份证图片调用方法
			Uploadfile frontImg = uploadfileService.uploadFiles(lotterybuyerOrExpertDTO.getIdNumberFrontImg(),request);
			if(null != frontImg)
			{
				lotterybuyerOrExpert.setIdNumberFrontImg(frontImg.getNewsUuid());
			}
			Uploadfile backImg = uploadfileService.uploadFiles(lotterybuyerOrExpertDTO.getIdNumberBackImg(),request);
			if(null != backImg)
			{
				lotterybuyerOrExpert.setIdNumberBackImg(backImg.getNewsUuid());
			}
			
			String imguri = "";//头像uri
			//创建融云用户id
			String token = rongyunImService.getUserToken(lotterybuyerOrExpert.getId(),
					lotterybuyerOrExpert.getName(), imguri);
			lotterybuyerOrExpert.setToken(token);
			
			lotterybuyerOrExpert.setCreator(lotterybuyerOrExpert.getCode());
			lotterybuyerOrExpert.setCreateTime(new Timestamp((System.currentTimeMillis())));
			lotterybuyerOrExpert.setModify(lotterybuyerOrExpert.getCode());
			lotterybuyerOrExpert.setModifyTime(new Timestamp((System.currentTimeMillis())));
			//保存用户信息
			lotterybuyerOrExpertService.save(lotterybuyerOrExpert);
		}
		catch(Exception e)
		{
			logger.error("error:",e);
		}
		
		
		
		return resultBean;
	}
	
	/**
	 * 保存或修改来自后台的信息
	* @Title: saveFromBackStage 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月18日 下午5:20:28 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/saveOrupdateFromBackStage" , method = RequestMethod.GET)
	public @ResponseBody ResultBean saveOrupdateFromBackStage(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO) 
	{
		ResultBean resultBean = new ResultBean();
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertById(lotterybuyerOrExpertDTO.getId());
		
		try
		{
			if(null != lotterybuyerOrExpert)
			{//修改用户
				//比较用户名是否修改
				lotterybuyerOrExpert.setName(lotterybuyerOrExpertDTO.getName());
				//TODO:确认可以在后台修改用户的哪些信息
				
				lotterybuyerOrExpert.setModify(lotterybuyerOrExpert.getCode());
				lotterybuyerOrExpert.setModifyTime(new Timestamp((System.currentTimeMillis())));
				
				lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
				
				resultBean.setStatus("success");
				resultBean.setMessage("修改成功");
			}
			else
			{//新建用户
				BeanUtil.copyBeanProperties(lotterybuyerOrExpert, lotterybuyerOrExpertDTO);
				lotterybuyerOrExpert.setId(UUID.randomUUID().toString());
				
				String imguri = "";//头像uri
				//创建融云用户id
				String token = rongyunImService.getUserToken(lotterybuyerOrExpert.getId(),
						lotterybuyerOrExpert.getName(), imguri);
				lotterybuyerOrExpert.setToken(token);
				
				lotterybuyerOrExpert.setCreator(lotterybuyerOrExpert.getCode());
				lotterybuyerOrExpert.setCreateTime(new Timestamp((System.currentTimeMillis())));
				lotterybuyerOrExpert.setModify(lotterybuyerOrExpert.getCode());
				lotterybuyerOrExpert.setModifyTime(new Timestamp((System.currentTimeMillis())));
				
				//保存用户信息
				lotterybuyerOrExpertService.save(lotterybuyerOrExpert);
				
				
				resultBean.setStatus("success");
				resultBean.setMessage("新建成功");
				
			}
		}
		catch(Exception e)
		{
			logger.error("error", e);
		}
		
		
		
		return resultBean;
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
		 
		 //TODO:删除用户的同时要处理用户的融云信息
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

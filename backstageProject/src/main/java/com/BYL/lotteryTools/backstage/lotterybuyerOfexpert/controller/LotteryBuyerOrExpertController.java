package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.controller;

import java.io.File;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryGroup.controller.OuterLotteryGroupController;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaApplyOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaApplybuyerAndGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaBindbuyerAndGroupService;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotteryStation.service.LotteryStationService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.RelaLBEUserAndLtcard;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.MyMD5Util;
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
	private static final Logger LOG = LoggerFactory.getLogger(LotteryBuyerOrExpertController.class);
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@Autowired
	private RongyunImService rongyunImService;//融云service层
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private RelaApplybuyerAndGroupService relaApplybuyerAndGroupService;
	
	@Autowired
	private RelaBindbuyerAndGroupService relaBindbuyerAndGroupService;
	
	@Autowired
	private LotteryStationService lotteryStationService;
	
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
			@RequestParam(value="isVirtual",required=false) String isVirtual,
			@RequestParam(value="isRobot",required=false) String isRobot,
			@RequestParam(value="telephone",required=false) String telephone,
			ModelMap model,HttpSession httpSession) throws Exception
	{
	 	Map<String,Object> returnData = new HashMap<String, Object>();
	 	
		QueryResult<LotterybuyerOrExpert> lQueryResult = lotterybuyerOrExpertService
				.getLotterybuyerOrExpertList(page, rows, name, provinceCode, isVirtual, isRobot,telephone);
				
		List<LotterybuyerOrExpert> list = lQueryResult.getResultList();
		Long totalrow = lQueryResult.getTotalRecord();
		
		//将实体转换为dto
		List<LotterybuyerOrExpertDTO> dtos = lotterybuyerOrExpertService.toDTOs(list);
		
		returnData.put("rows", dtos);
		returnData.put("total", totalrow);
	 	
	 	
	 	return returnData;
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
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,HttpSession httpSession,
			HttpServletRequest request) 
	{
		ResultBean resultBean = new ResultBean();
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertById(lotterybuyerOrExpertDTO.getId());
		
		try
		{
			if(null != lotterybuyerOrExpert)
			{//修改用户
				//比较用户名是否修改
				String lastName = lotterybuyerOrExpert.getName();
				boolean refreshFlag = false;
				if(!lastName.equals(lotterybuyerOrExpertDTO.getName()))
				{
					lotterybuyerOrExpert.setName(lotterybuyerOrExpertDTO.getName());
					refreshFlag = true;
				}
				//TODO:确认可以在后台修改用户的哪些信息
				
				//更改头像
				StringBuffer imguri = new StringBuffer();//头像uri
				Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(lotterybuyerOrExpertDTO.getTouXiang());
				if(null != uploadfile)
				{
					refreshFlag = true;
				}
				
				if(refreshFlag)
				{
					imguri.append(request.getSession().getServletContext().getRealPath(uploadfile.getUploadfilepath())).
					append(File.separator).
					append(uploadfile.getUploadRealName());
					LOG.info("touxiang",imguri.toString());//输出头像
					
					//刷新融云的用户头像
					CodeSuccessResult result = rongyunImService.refreshUser(lotterybuyerOrExpert.getId(),
							lotterybuyerOrExpert.getName(), imguri.toString());
					if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
					{
						LOG.error("refreshUser error:", result.getErrorMessage());
					}
				}
				
				lotterybuyerOrExpert.setColorCoins(lotterybuyerOrExpertDTO.getColorCoins());
				lotterybuyerOrExpert.setHandSel(lotterybuyerOrExpertDTO.getHandSel());
				lotterybuyerOrExpert.setIsRobot("0");
				lotterybuyerOrExpert.setName(lotterybuyerOrExpertDTO.getName());
				lotterybuyerOrExpert.setIsPhone(lotterybuyerOrExpertDTO.getIsPhone());
				lotterybuyerOrExpert.setAddress(lotterybuyerOrExpertDTO.getAddress());
				lotterybuyerOrExpert.setIsExpert(lotterybuyerOrExpertDTO.getIsExpert());
				//TODO：后续根据需要更新需要后台维护的用户信息
				
				lotterybuyerOrExpert.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				lotterybuyerOrExpert.setModifyTime(new Timestamp((System.currentTimeMillis())));
				
				lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
				
				resultBean.setStatus("success");
				resultBean.setMessage("修改成功");
			}
			else
			{//新建用户
				lotterybuyerOrExpert = new LotterybuyerOrExpert();
				BeanUtil.copyBeanProperties(lotterybuyerOrExpert, lotterybuyerOrExpertDTO);
				lotterybuyerOrExpert.setId(UUID.randomUUID().toString());
				
				StringBuffer imguri = new StringBuffer();//头像uri
				Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(lotterybuyerOrExpertDTO.getTouXiang());
				if(null != uploadfile)
				{
					imguri.append(request.getSession().getServletContext().getRealPath(uploadfile.getUploadfilepath())).
					append(File.separator).
					append(uploadfile.getUploadRealName());
					LOG.info("touxiang",imguri.toString());//输出头像
				}
				//创建融云用户id
				String token = rongyunImService.getUserToken(lotterybuyerOrExpert.getId(),
						lotterybuyerOrExpert.getName(), imguri.toString());
				lotterybuyerOrExpert.setPassword(MyMD5Util.getEncryptedPwd(lotterybuyerOrExpertDTO.getPassword()));
				lotterybuyerOrExpert.setToken(token);
				lotterybuyerOrExpert.setIsRobot("0");//机器人用户不可以被新建
				lotterybuyerOrExpert.setFromApp("0");//非app注册入口进入则为非app用户
				lotterybuyerOrExpert.setIsDeleted(Constants.IS_NOT_DELETED);
				lotterybuyerOrExpert.setCreator(LoginUtils.getAuthenticatedUserCode(httpSession));
				lotterybuyerOrExpert.setCreateTime(new Timestamp((System.currentTimeMillis())));
				lotterybuyerOrExpert.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				lotterybuyerOrExpert.setModifyTime(new Timestamp((System.currentTimeMillis())));
				
				//保存用户信息
				lotterybuyerOrExpertService.save(lotterybuyerOrExpert);
				
				
				resultBean.setStatus("success");
				resultBean.setMessage("新建成功");
				
			}
		}
		catch(Exception e)
		{
			LOG.error("error", e);
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
	@RequestMapping(value = "/deleteLborExpert")
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
			 		
			 		//删除头像图片
			 		List<Uploadfile> uploadfiles = uploadfileService.getUploadfilesByNewsUuid(lotterybuyerOrExpert.getTouXiang());
					uploadfileService.deleteUploadFile(uploadfiles, httpSession);
					
					//TODO:删除关联关系
					List<RelaBindOfLbuyerorexpertAndGroup> addGroups = lotterybuyerOrExpert.getRelaBindOfLbuyerorexpertAndGroups();
					for (RelaBindOfLbuyerorexpertAndGroup relaBindOfLbuyerorexpertAndGroup : addGroups) {
							relaBindbuyerAndGroupService.delete(relaBindOfLbuyerorexpertAndGroup);
						
					}
					List<RelaApplyOfLbuyerorexpertAndGroup> applyGroups = lotterybuyerOrExpert.getRelaApplyOfLbuyerorexpertAndGroups();
					for (RelaApplyOfLbuyerorexpertAndGroup relaApplyOfLbuyerorexpertAndGroup : applyGroups) {
							relaApplybuyerAndGroupService.delete(relaApplyOfLbuyerorexpertAndGroup);
						
					}
					List<RelaLBEUserAndLtcard> cards = lotterybuyerOrExpert.getRelaLBEUserAndLtcards();
					for (RelaLBEUserAndLtcard relaLBEUserAndLtcard : cards) {
							lotterybuyerOrExpertService.deleteRelaLBEUserAndLtcard(relaLBEUserAndLtcard);
					}
					lotterybuyerOrExpert.setRelaApplyOfLbuyerorexpertAndGroups(null);
					lotterybuyerOrExpert.setRelaBindOfLbuyerorexpertAndGroups(null);
					lotterybuyerOrExpert.setRelaLBEUserAndLtcards(null);
					lotterybuyerOrExpert.setRelaBindOfLbuyerorexpertAndLStations(null);
			 		lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
			 		
			 		//删除申请彩站申请未认证数据
			 		List<LotteryStation> stationList = lotteryStationService.getLotteryStationByManagerId(id);//获取申请认证的彩票站
			 		for (LotteryStation lotteryStation : stationList) {
			 			lotteryStation.setIsDeleted(Constants.IS_DELETED);
			 			lotteryStation.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 			lotteryStation.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 			lotteryStationService.update(lotteryStation);
					}
			 		
			 		 //日志输出
					 LOG.info("删除彩民数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				   
			 	}
			}
		 String returnMsg = "删除成功!";
		 resultBean.setStatus("success");
		 resultBean.setMessage(returnMsg);
		 
		 return resultBean;
			 
	 
	}
	
	/**
	 * 保存附件
	* @Title: saveFujian 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param realname
	* @param @param filename
	* @param @param uplId
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年4月21日 下午5:42:24 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/saveFujian", method = RequestMethod.GET)
	public @ResponseBody ResultBean  saveFujian(
			@RequestParam(value="realname",required=false) String realname,
			@RequestParam(value="filename",required=false) String filename,
			@RequestParam(value="uplId",required=false) String uplId,
			ModelMap model,HttpSession httpSession) throws Exception {
	 
			 ResultBean resultBean = new ResultBean();
			 String type=getExt(filename);
			 String uploadfilepath = "/upload/";
			 
			 Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(uplId);
			 
			 //因为一个应用只能有一个图片附件，所以当这个upId有数据的话就进行修改操作，如果没有数据就创建数据
			 if(null != uploadfile && !realname.equals(uploadfile.getUploadRealName()))//新添加的realname与原来的附件的realname不同时进行删除操作，为了避免同一方法被重复调用
			 {
				 //①：因为广告图片只有一个附件，所以在上传其他附件替换上一个附件时，要先把上一个附件文件删除
				 String savePath = httpSession.getServletContext().getRealPath("");//获取项目根路径
			     savePath = savePath +File.separator+ "upload"+File.separator;
			     //删除附件文件相关s
				 File dirFile = null;
				 boolean deleteFlag = false;//删除附件flag
				//2.删除附件
		 		dirFile = new File(savePath+uploadfile.getUploadRealName());
		 		LOG.info("待删除文件路径："+dirFile);
		        // 如果dir对应的文件不存在，或者不是一个目录，则退出
		    	deleteFlag = dirFile.delete();
		    	if(deleteFlag)
		    	{//删除附件(清空附件关联newsUuid)
		    		LOG.info("saveFujian==删除原附件文件数据--附件id="+uploadfile.getId()+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
		    	}
		    	else
		    	{
		    		LOG.error("saveFujian ERROR==没有找到要删除的附件文件或删除失败，附件路径为="+savePath+";File.exists="+dirFile.exists());
		    	}
			    //删除附件e
				 
				 //②：保存新的附件文件
				 uploadfile.setUploadFileName(filename);
				 uploadfile.setUploadRealName(realname);
				 uploadfile.setUploadfilepath(uploadfilepath);
				 uploadfile.setUploadContentType(type);
				 
				 //添加修改时间跟踪
				 uploadfile.setModify(uploadfile.getNewsUuid());//放置附件关联uuid
				 uploadfile.setModifyTime(new Timestamp(System.currentTimeMillis()));
				 
				 uploadfileService.update(uploadfile);
			 }
			 else
				 if(null == uploadfile)
				 {
						 uploadfile = new Uploadfile();
						 uploadfile.setNewsUuid(uplId);
						 uploadfile.setUploadFileName(filename);
						 uploadfile.setUploadRealName(realname);
						 uploadfile.setUploadfilepath(uploadfilepath);
						 uploadfile.setUploadContentType(type);
						
						 //添加修改时间跟踪
						 uploadfile.setCreator(uploadfile.getNewsUuid());//放置附件关联uuid
						 uploadfile.setModify(uploadfile.getNewsUuid());//放置附件关联uuid
						 uploadfile.setCreateTime(new Timestamp(System.currentTimeMillis()));
						 uploadfile.setModifyTime(new Timestamp(System.currentTimeMillis()));
						 uploadfile.setIsDeleted(Constants.IS_NOT_DELETED);
						 
						 uploadfileService.save(uploadfile);
				 }
			 
			 resultBean.setStatus("success");
			 
			 return resultBean;
			 
		 }
	
	/**
	 * 根据newsUuid删除附件
	* @Title: deleteImgsByNewsuuid 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param newsUuid
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年5月17日 上午9:14:54 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteImgsByNewsuuid", method = RequestMethod.GET)
	public @ResponseBody ResultBean deleteImgsByNewsuuid(
			@RequestParam(value="newsUuid",required=false) String newsUuid,
			ModelMap model,HttpSession httpSession) throws Exception {
	 
	 ResultBean resultBean = new ResultBean();
	 if(null != newsUuid)
	 {
		 List<Uploadfile> uploadfiles = uploadfileService.getUploadfilesByNewsUuid(newsUuid);
		 
		 uploadfileService.deleteUploadFile(uploadfiles, httpSession);
		
		 
		 resultBean.setUseFlag(true);
	 }
	
	 return resultBean;
 }
			
	 /**
	  * 
	  * @Title: getExt
	  * @Description: TODO
	  * @author:banna
	  * @return: String
	  */
	 private String getExt(String fileName) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
}

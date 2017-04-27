package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.BYL.lotteryTools.backstage.lotteryGroup.controller.OuterLotteryGroupController;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.SMSSendCodeResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.SMSVerifyCodeResult;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.MyMD5Util;

/**
 * 
* @Description: TODO(用户（彩民/专家）外部接口控制类) 
* @author banna
* @date 2017年3月14日 上午9:52:33
 */
@Controller
@RequestMapping("/outerLbuyerOrexpert")
public class OuterLotteryBuyerOrExpertController 
{
	private Logger logger = LoggerFactory.getLogger(OuterLotteryBuyerOrExpertController.class);
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@Autowired
	private RongyunImService rongyunImService;//融云service层
	
	@Autowired
	private UploadfileService uploadfileService;
	
	private Map<String,String> sessionMap = new HashMap<String, String>();
	
	private static final String DOMAIN="http://36.7.190.20:1881/";
	
	private static String morenTouxiang = "0";//默认头像newsUuid
	
	/**
	 * 获取注册用户手机验证码
	* @Title: getYanzhengma 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月18日 下午5:10:32 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getYanzhengmaForRegister" , method = RequestMethod.GET)
	public @ResponseBody ResultBean getYanzhengmaForRegister(@RequestParam(value="telephone",required=false) String telephone,
			HttpSession httpSession)
	{
		//TODO:调用第三方api给用户发送信息
		//判断当前手机号是否已经注册过
		ResultBean resultBean = new ResultBean();
		/*String templateId = "3dPWOS6S4Kx84BbwDRNwQ4";//设置模板id
		try {
			SMSSendCodeResult result = rongyunImService.sendCode(telephone, templateId, "86", null, null);
			sessionMap.put(telephone, result.getSessionId());
//			httpSession.setAttribute(telephone, result.getSessionId());//放置sessionid
//			httpSession.setMaxInactiveInterval(15*60);//15min后过期
			resultBean.setFlag(true);
			resultBean.setMessage("发送成功");
		} catch (Exception e) {
			logger.error("error:", e);
			resultBean.setFlag(false);
			resultBean.setMessage("发送失败,请稍候再试");
		}*/
		
		
		return resultBean;
		
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
	@RequestMapping(value="/saveFromApp", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> saveFromApp(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> result = new HashMap<String, Object>();
		//app端传的保存参数中，图片是file类型的文件，而后台是存储之后的图片id
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertByTelephone(lotterybuyerOrExpertDTO.getTelephone());
		try
		{
			if(null != lotterybuyerOrExpert)
			{//当前手机号已被注册
				result.put("status", false);
				result.put("message", "当前手机号已被注册");
			}
			else
			{//当前手机号未被注册
				//根据手机号获取sessionid
//				String sessionId = (String) httpSession.getAttribute(lotterybuyerOrExpertDTO.getTelephone());
				/*String sessionId = sessionMap.get(lotterybuyerOrExpertDTO.getTelephone());
				SMSVerifyCodeResult yanzhengma = null;
				if(null != sessionId)
				{
					 yanzhengma = rongyunImService.verifyCode(sessionId, lotterybuyerOrExpertDTO.getYanzhengma());
				}
				
				if(yanzhengma.getSuccess())
				{*/
					//注册时彩币、彩金的金额都是null
					lotterybuyerOrExpert = new LotterybuyerOrExpert();
					lotterybuyerOrExpertDTO.setHandSel(new BigDecimal(0));
					lotterybuyerOrExpertDTO.setColorCoins(new BigDecimal(0));
					BeanUtil.copyBeanProperties(lotterybuyerOrExpert, lotterybuyerOrExpertDTO);
					lotterybuyerOrExpert.setId(UUID.randomUUID().toString());
					//对密码进行加密
					lotterybuyerOrExpert.setPassword(MyMD5Util.getEncryptedPwd(lotterybuyerOrExpertDTO.getPassword()));
					
					StringBuffer imguri = new StringBuffer();//头像uri（注册时默认不进行头像的上传）
					//添加默认头像(TODO:需要初始化一张默认头像图片到upload文件夹和uploadfile表中)
					lotterybuyerOrExpert.setTouXiang(morenTouxiang);//关联新头像
					Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(morenTouxiang);
					//刷新融云用户信息,将图片信息同步
					imguri.append(OuterLotteryBuyerOrExpertController.DOMAIN)
							.append(request.getContextPath()).append(uploadfile.getUploadfilepath()).append(uploadfile.getUploadRealName());
					//创建融云用户id
					String token = rongyunImService.getUserToken(lotterybuyerOrExpert.getId(),
							lotterybuyerOrExpert.getName(), imguri.toString());
					lotterybuyerOrExpert.setToken(token);
					
					lotterybuyerOrExpert.setIsPhone("1");//从app端走注册接口的一定是手机用户
					lotterybuyerOrExpert.setIsExpert("0");//注册时用户的默认身份是彩民
					lotterybuyerOrExpert.setIsVirtual("0");//是否为虚拟用户（虚拟用户是由公司来创建的，没有实际意义）
					lotterybuyerOrExpert.setIsRobot("0");//从app端注册的用户都不是机器人用户
					lotterybuyerOrExpert.setIsStationOwner("0");//在注册时默认都不是站主
					lotterybuyerOrExpert.setFromApp("1");//app注册入口进入则为app用户
					
					lotterybuyerOrExpert.setIsDeleted(Constants.IS_NOT_DELETED);
					lotterybuyerOrExpert.setCreator(lotterybuyerOrExpert.getId());
					lotterybuyerOrExpert.setCreateTime(new Timestamp((System.currentTimeMillis())));
					lotterybuyerOrExpert.setModify(lotterybuyerOrExpert.getId());
					lotterybuyerOrExpert.setModifyTime(new Timestamp((System.currentTimeMillis())));
					//保存用户信息
					lotterybuyerOrExpertService.save(lotterybuyerOrExpert);
					BeanUtil.copyBeanProperties(lotterybuyerOrExpertDTO, lotterybuyerOrExpert);
					result.put("status", true);
					result.put("message", "注册成功");
					result.put("user", lotterybuyerOrExpertDTO);
				/*}
				else
				{//手机验证码验证失败
					result.put("status", false);
					result.put("message", yanzhengma.getErrorMessage());//放置验证码错误信息
				}
				*/
				
			}
		
		}
		catch(Exception e)
		{
			logger.error("error:",e);
			result.put("status", false);
			result.put("message", "注册失败");
		}
		
		
		return result;
	}
	
	/**
	 * 用户登录（使用手机号和密码登录）若登录成功则返回用户信息，若失败则不返回信息
	* @Title: login 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @param request
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月20日 上午10:11:02 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> login(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request)
	{
	    Map<String,Object> map = new HashMap<String, Object>();
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertByTelephone(lotterybuyerOrExpertDTO.getTelephone());
		String originPassword = lotterybuyerOrExpert.getPassword();//修改前密码
		boolean flag=false;
		try {
			flag=MyMD5Util.validPassword(lotterybuyerOrExpertDTO.getPassword(), originPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(flag)
		{
			map.put("flag", true);
			map.put("messsage", "登录成功");
			lotterybuyerOrExpertDTO = lotterybuyerOrExpertService.toDTO(lotterybuyerOrExpert);
			map.put("userDto", lotterybuyerOrExpertDTO);
		}
		else
		{
			map.put("flag", false);
			map.put("messsage", "登录失败");
			map.put("userDto", null);
		}
		
		
		
		return map;
	}
	
	/**
	 *修改密码（可以多次修改）在验证前要进行手机的验证，手机验证成功后再进行秘密的修改
	* @Title: updateTouxiang 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @param request
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月20日 上午8:59:40 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/updatePassword", method = RequestMethod.GET)
	public @ResponseBody ResultBean updatePassword(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request)
	{
		ResultBean resultBean = new ResultBean();
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertById(lotterybuyerOrExpertDTO.getId());
		try
		{
			lotterybuyerOrExpert.setPassword(MyMD5Util.getEncryptedPwd(lotterybuyerOrExpertDTO.getPassword()));
		}
		catch(Exception e)
		{
			logger.error("error:", e);
		}
		
		lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
		
		resultBean.setFlag(true);
		resultBean.setMessage("修改密码成功");
		
		
		return resultBean;
	}
	
	/**
	 * 校验输入密码是否和用户密码符合
	* @Title: checkPassword 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @param request
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月21日 上午10:55:58 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/checkPassword", method = RequestMethod.GET)
	public @ResponseBody ResultBean checkPassword(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request)
	{
		ResultBean resultBean = new ResultBean();
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertById(lotterybuyerOrExpertDTO.getId());
		
		try
		{
			boolean flag = MyMD5Util.validPassword(lotterybuyerOrExpertDTO.getPassword(), lotterybuyerOrExpert.getPassword());
			resultBean.setFlag(flag);
			if(flag)
			{
				resultBean.setMessage("原密码输入正确");
			}
			else
			{
				resultBean.setMessage("原密码输入错误");
			}
			
		}
		catch(Exception e)
		{
			logger.error("error:", e);
			resultBean.setFlag(false);
			resultBean.setMessage("请求错误");
		}
		
		
		return resultBean;
	}
	
	/**
	 * 上传附件
	* @Title: uploadFile 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param files
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月26日 上午8:53:05 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/uploadFile", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> uploadFile(
			@RequestParam(value = "file",required = false) MultipartFile[] files,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		Uploadfile uploadfile =null;
		try {
			for (MultipartFile multipartFile : files) {
				 uploadfile = uploadfileService.uploadFiles(multipartFile,request);
			}
			
		} catch (Exception e) {
			logger.error("error:", e);
		}
		map.put("id", uploadfile.getNewsUuid());
		return map;
	}
	
	/**
	 * 更改个人信息（头像，彩聊号，城市，性别，个人简介，地址，坐标，邮政编码）（彩聊号只能更改一次，已经添加后不可以进行修改操作）
	* @Title: updateCailiaohao 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @param request
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月20日 上午9:00:19 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/updateUser",method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> updateUser(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertById(lotterybuyerOrExpertDTO.getId());
		//上传头像（若头像上传了新的，则要进行用户信息刷新）
		if(null != lotterybuyerOrExpertDTO.getTouXiang())//获取头像newsUuid是否为空
		{
			lotterybuyerOrExpert.setTouXiang(lotterybuyerOrExpertDTO.getTouXiang());//关联新头像
			Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(lotterybuyerOrExpertDTO.getTouXiang());
			//刷新融云用户信息,将图片信息同步(TODO:同步头像必须带ip，放在外网才可以)
			CodeSuccessResult result = rongyunImService.refreshUser(lotterybuyerOrExpert.getId(),
					null, OuterLotteryBuyerOrExpertController.DOMAIN+request.getContextPath()+uploadfile.getUploadfilepath()+uploadfile.getUploadRealName());
			if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
			{
				logger.error("融云同步头像失败", result.getErrorMessage());
			}
			
			//删除旧头像(也可以定时批量删除)
			if(!OuterLotteryBuyerOrExpertController.morenTouxiang.equals(lotterybuyerOrExpertDTO.getLastTouXiang())
					&&null != lotterybuyerOrExpertDTO.getLastTouXiang()&&!"".equals(lotterybuyerOrExpertDTO.getLastTouXiang()))
			{
				logger.info("删除附件",lotterybuyerOrExpertDTO.getLastTouXiang());
				Uploadfile lastTouxiang = uploadfileService.getUploadfileByNewsUuid(lotterybuyerOrExpertDTO.getLastTouXiang());
				if(null != lastTouxiang)
				{//若存在旧头像，则要进行删除操作
					uploadfileService.delete(lastTouxiang, httpSession);
				}
			}
		}
		
		//修改昵称
		if(null != lotterybuyerOrExpertDTO.getName() && !"".equals(lotterybuyerOrExpertDTO.getName()))
		{
			lotterybuyerOrExpert.setName(lotterybuyerOrExpertDTO.getName());
			CodeSuccessResult result = rongyunImService.refreshUser(lotterybuyerOrExpert.getId(),
					lotterybuyerOrExpertDTO.getName(), null);
			if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
			{
				logger.error("融云同步用户名失败", result.getErrorMessage());
			}
			
		}
		
		
		
		//添加城市，省份和市
		if(null != lotterybuyerOrExpertDTO.getProvinceCode() && !"".equals(lotterybuyerOrExpertDTO.getProvinceCode()))
		{
			lotterybuyerOrExpert.setProvinceCode(lotterybuyerOrExpertDTO.getProvinceCode());
		}
		if(null != lotterybuyerOrExpertDTO.getCityCode()&& !"".equals(lotterybuyerOrExpertDTO.getCityCode()))
		{
			lotterybuyerOrExpert.setCityCode(lotterybuyerOrExpertDTO.getCityCode());
		}
		
		//性别，0：女 1：男
		if(null != lotterybuyerOrExpertDTO.getSex() && !"".equals(lotterybuyerOrExpertDTO.getSex()))
		{
			lotterybuyerOrExpert.setSex(lotterybuyerOrExpertDTO.getSex());
		}
		
		
		//个人简介
		if(null != lotterybuyerOrExpertDTO.getSignature() && !"".equals(lotterybuyerOrExpertDTO.getSignature()))
		{
			lotterybuyerOrExpert.setSignature(lotterybuyerOrExpertDTO.getSignature());
		}
		
		//地址
		if(null != lotterybuyerOrExpertDTO.getAddress())
		{
			lotterybuyerOrExpert.setAddress(lotterybuyerOrExpertDTO.getAddress());
		}
		
		//坐标
		if(null != lotterybuyerOrExpertDTO.getCoordinate())
		{
			lotterybuyerOrExpert.setCoordinate(lotterybuyerOrExpertDTO.getCoordinate());
		}
		
		
		//邮政编码
		if(null != lotterybuyerOrExpertDTO.getPostCode())
		{
			lotterybuyerOrExpert.setPostCode(lotterybuyerOrExpertDTO.getPostCode());
		}
		
		//修改彩聊号
		boolean caiFlag = true;
		if("".equals(lotterybuyerOrExpert.getCailiaoName())|| null == lotterybuyerOrExpert.getCailiaoName())
		{//之前的彩聊号没填写过才可以进行填写
			//判断彩聊号是否唯一
			//根据彩聊号获取用户专家数据，若有返回数据，则彩聊号当前已存在，不可以被更新
			List<LotterybuyerOrExpert> list = lotterybuyerOrExpertService.
					getLotterybuyerOrExpertByCailiaoName(lotterybuyerOrExpertDTO.getCailiaoName());
			if(null != list&& list.size()>0)
			{
				caiFlag = false;
			}
			
			lotterybuyerOrExpert.setCailiaoName(lotterybuyerOrExpertDTO.getCailiaoName());//添加彩聊号
		}
		
		if(caiFlag)
		{//若彩聊号唯一，则可以进行修改操作
			lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
			try 
			{
				lotterybuyerOrExpertDTO = lotterybuyerOrExpertService.toDTO(lotterybuyerOrExpert);
				
				map.put("flag", true);
				map.put("message", "修改成功");
				map.put("userDto", lotterybuyerOrExpertDTO);
			} catch (Exception e) {
				logger.error("error:", e);
				map.put("flag", false);
				map.put("message", "修改失败");
			}
		}
		else
		{
			map.put("flag", false);
			map.put("message", "彩聊号不唯一");
		}
		
		
		
		return map;
	}
	
	
	
	
	
	
}

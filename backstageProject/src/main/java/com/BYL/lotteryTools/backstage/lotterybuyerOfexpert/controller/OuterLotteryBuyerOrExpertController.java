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
import com.BYL.lotteryTools.common.exception.GlobalOuterExceptionHandler;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.MyMD5Util;
import com.BYL.lotteryTools.common.util.TokenUtil;

/**
 * 
* @Description: TODO(用户（彩民/专家）外部接口控制类) 
* @author banna
* @date 2017年3月14日 上午9:52:33
 */
@Controller
@RequestMapping("/outerLbuyerOrexpert")
public class OuterLotteryBuyerOrExpertController extends GlobalOuterExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(OuterLotteryBuyerOrExpertController.class);
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@Autowired
	private RongyunImService rongyunImService;//融云service层
	
	@Autowired
	private UploadfileService uploadfileService;
	
	public static Map<String,String> sessionMap = new HashMap<String, String>();
	
	public static final String DOMAIN="http://36.7.190.20:1881/";
	
	public static String morenTouxiang = "0";//默认头像newsUuid
	
	
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
		String templateId = "3dPWOS6S4Kx84BbwDRNwQ4";//设置模板id
		try {
			SMSSendCodeResult result = rongyunImService.sendCode(telephone, templateId, "86", null, null);
			sessionMap.put(telephone, result.getSessionId());
//			httpSession.setAttribute(telephone, result.getSessionId());//放置sessionid
//			httpSession.setMaxInactiveInterval(15*60);//15min后过期
			resultBean.setFlag(true);
			resultBean.setCode(Constants.SUCCESS_CODE);
			resultBean.setMessage("发送成功");
		} catch (Exception e) {
			LOG.error("error:", e);
			resultBean.setFlag(false);
			resultBean.setCode(Constants.SERVER_FAIL_CODE);
			resultBean.setMessage("发送失败,请稍候再试");
		}
		
		
		return resultBean;
		
	}
	
	/**
	 * 校验当前手机号是否已被注册
	* @Title: checkTelephoneIsRegister 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月31日 下午5:27:58 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/checkTelephoneIsRegister", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> checkTelephoneIsRegister(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> result = new HashMap<String, Object>();
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertByTelephone(lotterybuyerOrExpertDTO.getTelephone());
		
		if(null != lotterybuyerOrExpert)
		{//当前手机号已被注册
			result.put("status", false);
			result.put(Constants.FLAG_STR, false);
			result.put(Constants.CODE_STR, Constants.FAIL_CODE);
			result.put(Constants.MESSAGE_STR, "当前手机号已被注册");
		}
		else
		{
			result.put(Constants.FLAG_STR, true);
			result.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			result.put(Constants.MESSAGE_STR, "当前手机号未被注册");
		}
		
		
		return result;
	}
	
	/**
	 * 校验输入验证码是否正确
	* @Title: checkYanzhengma 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param yanzhengma
	* @param @param telephone
	* @param @param request
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月1日 上午10:13:14 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/checkYanzhengma", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> checkYanzhengma(
			@RequestParam(value = "yanzhengma",required = true) String yanzhengma,
			@RequestParam(value = "telephone",required = true) String telephone,
			HttpServletRequest request,HttpSession httpSession) throws Exception
	{
		Map<String,Object> result = new HashMap<String, Object>();
		
		String sessionId = sessionMap.get(telephone);
		SMSVerifyCodeResult sessionYanzhengma = null;
		if(null != sessionId)
		{
			sessionYanzhengma = rongyunImService.verifyCode(sessionId, yanzhengma);
			
			if(sessionYanzhengma.getSuccess())
			{
				result.put(Constants.FLAG_STR, true);
				result.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				result.put(Constants.MESSAGE_STR, "验证码输入正确");
			}
			else
			{
				result.put(Constants.FLAG_STR, false);
				result.put(Constants.CODE_STR, Constants.YZM_INPUT_ERROR_CODE);
				result.put(Constants.MESSAGE_STR, "验证码输入错误");
			}
		}
		else
		{
			result.put(Constants.FLAG_STR, false);
			result.put(Constants.CODE_STR, Constants.YZM_GET_ERROR_CODE);
			result.put(Constants.MESSAGE_STR, "请重新获取验证码");
		}
		return result;
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
				result.put(Constants.FLAG_STR, false);
				result.put(Constants.CODE_STR, Constants.FAIL_CODE);
				result.put(Constants.MESSAGE_STR, "当前手机号已被注册");
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
					LotterybuyerOrExpertDTO dto = lotterybuyerOrExpertService.toDTO(lotterybuyerOrExpert);
					dto.setUserToken(TokenUtil.generateToken(dto.getTelephone(), lotterybuyerOrExpertDTO.getPassword()));
					result.put("status", true);
					result.put(Constants.FLAG_STR, true);
					result.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
					result.put(Constants.MESSAGE_STR, "注册成功");
					result.put("user", dto);
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
			LOG.error(Constants.ERROR_STR,e);
			result.put("status", false);
			result.put(Constants.FLAG_STR, false);
			result.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
			result.put(Constants.MESSAGE_STR, "注册失败");
		}
		finally
		{
			//置无用对象为null，告知GC可以进行回收
			lotterybuyerOrExpert = null;
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
			LotterybuyerOrExpertDTO dto,
			HttpServletRequest request)
	{
	    Map<String,Object> map = new HashMap<String, Object>();
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertByTelephone(dto.getTelephone());
		if(null != lotterybuyerOrExpert)
		{
			String originPassword = lotterybuyerOrExpert.getPassword();//修改前密码
			boolean flag=false;
			try {
				flag=MyMD5Util.validPassword(dto.getPassword(), originPassword);
			
				if(flag)
				{
					map.put(Constants.FLAG_STR, true);
					map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
					map.put(Constants.MESSAGE_STR, "登录成功");
					dto = lotterybuyerOrExpertService.toDTO(lotterybuyerOrExpert);
					//放置usertoken
					dto.setUserToken(TokenUtil.generateToken(dto.getTelephone(), originPassword));
					map.put("userDto", dto);
				}
				else
				{
					map.put(Constants.FLAG_STR, false);
					map.put(Constants.CODE_STR, Constants.FAIL_CODE);
					map.put(Constants.MESSAGE_STR, "密码错误!");
					map.put("userDto", null);
				}
			} catch (NoSuchAlgorithmException e) {
				LOG.error("error:", e);
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
				map.put(Constants.MESSAGE_STR, "服务器错误!");
			} catch (UnsupportedEncodingException e) {
				LOG.error("error:", e);
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
				map.put(Constants.MESSAGE_STR, "服务器错误!");
			}
			finally{
				lotterybuyerOrExpert = null;
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.FAIL_CODE);
			map.put(Constants.MESSAGE_STR, "用户名不存在!");
		}
		
		
		return map;
	}
	
	/**
	 * 根据token获取用户信息
	* @Title: getUserByToken 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月21日 下午3:03:22 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getUserByToken", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getUserByToken(
			LotterybuyerOrExpertDTO dto,
			HttpServletRequest request)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(null != dto.getUserToken() && !"".equals(dto.getUserToken()))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(dto.getUserToken());
			if(!tokenFlag)
			{//token不相同
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.CODE_STR, Constants.TOKEN_IS_PASS_CODE);
				map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
			}
			else
			{
				String tel = TokenUtil.getTelephoneByToken(dto.getUserToken());//根据token获取手机号
				LotterybuyerOrExpert user = lotterybuyerOrExpertService.getLotterybuyerOrExpertByTelephone(tel);
				
				//token验证成功
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				map.put(Constants.MESSAGE_STR, "获取成功");
				dto = lotterybuyerOrExpertService.toDTO(user);
				map.put("userDto", dto);
			}
			
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.TOKEN_IS_NOT_EXIST_CODE);
			map.put(Constants.MESSAGE_STR, "token值不存在!");
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
		
		if(null != lotterybuyerOrExpertDTO.getUserToken() && !"".equals(lotterybuyerOrExpertDTO.getUserToken()))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(lotterybuyerOrExpertDTO.getUserToken());
			if(!tokenFlag)
			{//token不相同
				resultBean.setFlag(false);
				resultBean.setCode(Constants.TOKEN_IS_PASS_CODE);
				resultBean.setMessage("token过期,请重新登录!");
			}
			else
			{
				String tel = TokenUtil.getTelephoneByToken(lotterybuyerOrExpertDTO.getUserToken());
				/*LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
						getLotterybuyerOrExpertById(lotterybuyerOrExpertDTO.getId());*/
				LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
						getLotterybuyerOrExpertByTelephone(tel);
				
				try
				{
					lotterybuyerOrExpert.setPassword(MyMD5Util.getEncryptedPwd(lotterybuyerOrExpertDTO.getPassword()));
					lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
					
					resultBean.setFlag(true);
					resultBean.setCode(Constants.SUCCESS_CODE);
					resultBean.setMessage("修改密码成功");
				}
				catch(Exception e)
				{
					LOG.error("error:", e);
					resultBean.setFlag(false);
					resultBean.setCode(Constants.SERVER_FAIL_CODE);
					resultBean.setMessage("服务器错误!");
				}
				finally{
					lotterybuyerOrExpertDTO = null;
					lotterybuyerOrExpert = null;
				}
			}
		
		}
		else
		{
			resultBean.setFlag(false);
			resultBean.setCode(Constants.TOKEN_IS_NOT_EXIST_CODE);
			resultBean.setMessage("token值不存在!");
		}
		
		return resultBean;
	}
	
	/**
	 * 修改密码
	* @Title: findPassword 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @param request
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月22日 下午6:48:59 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/findPassword", method = RequestMethod.GET)
	public @ResponseBody ResultBean findPassword(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request)
	{
		ResultBean resultBean = new ResultBean();
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertByTelephone(lotterybuyerOrExpertDTO.getTelephone());
		try
		{
			lotterybuyerOrExpert.setPassword(MyMD5Util.getEncryptedPwd(lotterybuyerOrExpertDTO.getPassword()));
			lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
			
			resultBean.setFlag(true);
			resultBean.setCode(Constants.SUCCESS_CODE);
			resultBean.setMessage("修改密码成功");
		}
		catch(Exception e)
		{
			LOG.error("error:", e);
			resultBean.setFlag(false);
			resultBean.setCode(Constants.SERVER_FAIL_CODE);
			resultBean.setMessage("服务器错误!");
		}
		finally{
			lotterybuyerOrExpertDTO = null;
			lotterybuyerOrExpert = null;
		}
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
		
		if(null != lotterybuyerOrExpertDTO.getUserToken() && !"".equals(lotterybuyerOrExpertDTO.getUserToken()))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(lotterybuyerOrExpertDTO.getUserToken());
			if(!tokenFlag)
			{//token不相同
				resultBean.setFlag(false);
				resultBean.setCode(Constants.TOKEN_IS_PASS_CODE);
				resultBean.setMessage("token过期,请重新登录!");
			}
			else
			{
				LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
						getLotterybuyerOrExpertById(lotterybuyerOrExpertDTO.getId());
				
				try
				{
					boolean flag = MyMD5Util.validPassword(lotterybuyerOrExpertDTO.getPassword(), lotterybuyerOrExpert.getPassword());
					resultBean.setFlag(flag);
					if(flag)
					{
						resultBean.setFlag(true);
						resultBean.setCode(Constants.SUCCESS_CODE);
						resultBean.setMessage("原密码输入正确");
					}
					else
					{
						resultBean.setFlag(false);
						resultBean.setCode(Constants.FAIL_CODE);
						resultBean.setMessage("原密码输入错误");
					}
					
				}
				catch(Exception e)
				{
					LOG.error("error:", e);
					resultBean.setFlag(false);
					resultBean.setCode(Constants.SERVER_FAIL_CODE);
					resultBean.setMessage("请求错误");
				}
				finally{
					lotterybuyerOrExpertDTO = null;
					lotterybuyerOrExpert = null;
				}
			}
		}
		else
		{
			resultBean.setFlag(false);
			resultBean.setCode(Constants.TOKEN_IS_NOT_EXIST_CODE);
			resultBean.setMessage("token值不存在!");
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
		String newsUuid = UUID.randomUUID().toString();
		try {
			for (MultipartFile multipartFile : files) {
				 uploadfile = uploadfileService.uploadFiles(multipartFile,request,newsUuid);
			}
			
		} catch (Exception e) {
			LOG.error("error:", e);
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
			map.put(Constants.MESSAGE_STR, "服务器错误");
		}
		if(null != uploadfile)
		{
			map.put("id", uploadfile.getNewsUuid());
			map.put(Constants.FLAG_STR, true);
			map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			map.put(Constants.MESSAGE_STR, "获取成功");
		}
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
	@RequestMapping(value="/updateUser")
	public @ResponseBody Map<String,Object> updateUser(
			LotterybuyerOrExpertDTO dto,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(null != dto.getUserToken() && !"".equals(dto.getUserToken()))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(dto.getUserToken());
			if(!tokenFlag)
			{//token不相同
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.CODE_STR, Constants.TOKEN_IS_PASS_CODE);
				map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
			}
			else
			{
				LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
						getLotterybuyerOrExpertById(dto.getId());
				
				if(null != lotterybuyerOrExpert)
				{
					if(null != dto.getTouXiangImg())
					{
						Uploadfile uploadfile =null;
						String newsUuid = UUID.randomUUID().toString();
						try 
						{
							 uploadfile = uploadfileService.uploadFiles(dto.getTouXiangImg(),request,newsUuid);
						} 
						catch (Exception e) 
						{
							LOG.error(Constants.ERROR_STR, e);
						}
						
						//删除旧头像(也可以定时批量删除,若为基础头像，则不可以进行删除)
						if(!OuterLotteryBuyerOrExpertController.morenTouxiang.equals(lotterybuyerOrExpert.getTouXiang()))
						{
							LOG.info("删除附件",dto.getLastTouXiang());
							Uploadfile lastTouxiang = uploadfileService.getUploadfileByNewsUuid(lotterybuyerOrExpert.getTouXiang());
							if(null != lastTouxiang)
							{//若存在旧头像，则要进行删除操作
								uploadfileService.delete(lastTouxiang, httpSession);
							}
						}
						//绑定新头像
						if(null != uploadfile)
						{
							lotterybuyerOrExpert.setTouXiang(uploadfile.getNewsUuid());//关联新头像
						}
						
					}
					
					//修改昵称
					if(null != dto.getName() && !"".equals(dto.getName()))
					{
						lotterybuyerOrExpert.setName(dto.getName());
						CodeSuccessResult result = rongyunImService.refreshUser(lotterybuyerOrExpert.getId(),
								dto.getName(), null);
							if(null != result&&!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
							{
								LOG.error("融云同步用户名失败", result.getErrorMessage());
							}
						
						
					}
					
					//添加城市，省份和市
					if(null != dto.getProvinceCode() && !"".equals(dto.getProvinceCode()))
					{
						lotterybuyerOrExpert.setProvinceCode(dto.getProvinceCode());
					}
					if(null != dto.getCityCode()&& !"".equals(dto.getCityCode()))
					{
						lotterybuyerOrExpert.setCityCode(dto.getCityCode());
					}
					
					//性别，0：女 1：男
					if(null != dto.getSex() && !"".equals(dto.getSex()))
					{
						lotterybuyerOrExpert.setSex(dto.getSex());
					}
					
					
					//个人简介
					if(null != dto.getSignature() && !"".equals(dto.getSignature()))
					{
						lotterybuyerOrExpert.setSignature(dto.getSignature());
					}
					
					//地址
					if(null != dto.getAddress())
					{
						lotterybuyerOrExpert.setAddress(dto.getAddress());
					}
					
					//坐标
					if(null != dto.getCoordinate())
					{
						lotterybuyerOrExpert.setCoordinate(dto.getCoordinate());
					}
					
					
					//邮政编码
					if(null != dto.getPostCode())
					{
						lotterybuyerOrExpert.setPostCode(dto.getPostCode());
					}
					
					//修改彩聊号
					boolean caiFlag = true;
					if("".equals(lotterybuyerOrExpert.getCailiaoName())|| null == lotterybuyerOrExpert.getCailiaoName())
					{//之前的彩聊号没填写过才可以进行填写
						//判断彩聊号是否唯一
						//根据彩聊号获取用户专家数据，若有返回数据，则彩聊号当前已存在，不可以被更新
						List<LotterybuyerOrExpert> list = lotterybuyerOrExpertService.
								getLotterybuyerOrExpertByCailiaoName(dto.getCailiaoName());
						if(null != list&& list.size() != 0)
						{
							caiFlag = false;
						}
						
						lotterybuyerOrExpert.setCailiaoName(dto.getCailiaoName());//添加彩聊号
					}
					
					if(caiFlag)
					{//若彩聊号唯一，则可以进行修改操作
						lotterybuyerOrExpert.setModify("app");
						lotterybuyerOrExpert.setModifyTime(new Timestamp(System.currentTimeMillis()));
						lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
						try 
						{
							dto = lotterybuyerOrExpertService.toDTO(lotterybuyerOrExpert);
							
							map.put(Constants.FLAG_STR, true);
							map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
							map.put(Constants.MESSAGE_STR, "修改成功");
							map.put("userDto", dto);
						} catch (Exception e) {
							LOG.error(Constants.ERROR_STR, e);
							map.put(Constants.FLAG_STR, false);
							map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
							map.put(Constants.MESSAGE_STR, "服务器错误");
						}
					}
					else
					{
						map.put(Constants.FLAG_STR, false);
						map.put(Constants.CODE_STR, Constants.FAIL_CODE);
						map.put(Constants.MESSAGE_STR, "彩聊号不唯一");
					}
				}
				else
				{
					map.put(Constants.FLAG_STR, false);
					map.put(Constants.CODE_STR, Constants.FAIL_CODE);
					map.put(Constants.MESSAGE_STR, "缺少参数:id,无法获取需要修改的用户信息");
				}
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.TOKEN_IS_NOT_EXIST_CODE);
			map.put(Constants.MESSAGE_STR, "token值不存在!");
		}
		
		return map;
	}
	
	
	
	
	
	
}

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
		//判断当前手机号是否已经注册过
		ResultBean resultBean = lotterybuyerOrExpertService.getYanzhengmaForRegister(telephone);
		
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
			result.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_TEL_IS_REGITED);
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
	
	@RequestMapping(value="/checkYanzhengma", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> checkYanzhengma(
			@RequestParam(value = "yanzhengma",required = true) String yanzhengma,
			@RequestParam(value = "telephone",required = true) String telephone,
			HttpServletRequest request,HttpSession httpSession) throws Exception
	{
		Map<String,Object> result = lotterybuyerOrExpertService.checkYanzhengma(yanzhengma, telephone);
		
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
		Map<String,Object> result = lotterybuyerOrExpertService.saveFromApp(lotterybuyerOrExpertDTO, request);
		
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
					map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_PWD_ERROR);
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
			map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_TEL_IS_NOT_EXIST);
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
		
		String tel = TokenUtil.getTelephoneByToken(dto.getUserToken());//根据token获取手机号
		LotterybuyerOrExpert user = lotterybuyerOrExpertService.getLotterybuyerOrExpertByTelephone(tel);
		
		//token验证成功
		map.put(Constants.FLAG_STR, true);
		map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		map.put(Constants.MESSAGE_STR, "获取成功");
		dto = lotterybuyerOrExpertService.toDTO(user);
		map.put("userDto", dto);
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
			resultBean.setResultCode(Constants.SUCCESS_CODE);
			resultBean.setMessage("修改密码成功");
		}
		catch(Exception e)
		{
			LOG.error("error:", e);
			resultBean.setFlag(false);
			resultBean.setResultCode(Constants.SERVER_FAIL_CODE);
			resultBean.setMessage("服务器错误!");
		}
		finally{
			lotterybuyerOrExpertDTO = null;
			lotterybuyerOrExpert = null;
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
			resultBean.setResultCode(Constants.SUCCESS_CODE);
			resultBean.setMessage("修改密码成功");
		}
		catch(Exception e)
		{
			LOG.error("error:", e);
			resultBean.setFlag(false);
			resultBean.setResultCode(Constants.SERVER_FAIL_CODE);
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
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
				getLotterybuyerOrExpertById(lotterybuyerOrExpertDTO.getId());
		
		try
		{
			boolean flag = MyMD5Util.validPassword(lotterybuyerOrExpertDTO.getPassword(), lotterybuyerOrExpert.getPassword());
			resultBean.setFlag(flag);
			if(flag)
			{
				resultBean.setFlag(true);
				resultBean.setResultCode(Constants.SUCCESS_CODE);
				resultBean.setMessage("原密码输入正确");
			}
			else
			{
				resultBean.setFlag(false);
				resultBean.setResultCode(Constants.FAIL_CODE_OF_ORIGIN_PWD_ERROR);
				resultBean.setMessage("原密码输入错误");
			}
			
		}
		catch(Exception e)
		{
			LOG.error("error:", e);
			resultBean.setFlag(false);
			resultBean.setResultCode(Constants.SERVER_FAIL_CODE);
			resultBean.setMessage("请求错误");
		}
		finally{
			lotterybuyerOrExpertDTO = null;
			lotterybuyerOrExpert = null;
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
				map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_CAILIAO_IS_NOT_ONLY);
				map.put(Constants.MESSAGE_STR, "彩聊号不唯一");
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.FAIL_CODE);
			map.put(Constants.MESSAGE_STR, "缺少参数:id,无法获取需要修改的用户信息");
		}

		return map;
	}
	
	/**
	 * 微信登录
	* @Title: weixinLogin 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年8月7日 下午1:30:08 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/weixinLogin")
	public @ResponseBody Map<String,Object> weixinLogin(
			LotterybuyerOrExpertDTO dto,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(null != dto.getWxOpenId()&&!"".equals(dto.getWxOpenId()))
		{
			LotterybuyerOrExpert user = lotterybuyerOrExpertService.
					getLotterybuyerOrExpertByWxOpenId(dto.getWxOpenId());
			if(null != user)
			{
				map.put(Constants.FLAG_STR, true);
				dto = lotterybuyerOrExpertService.toDTO(user);
				dto.setUserToken(TokenUtil.generateToken(dto.getTelephone(), dto.getPassword()));
				map.put("userDto",dto);
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				map.put(Constants.MESSAGE_STR, "登录成功");//请注册后使用微信登录
			}
			else
			{//当前用户未使用微信注册
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_NOT_FOUD_USER_BY_OPEN_ID);
				map.put(Constants.MESSAGE_STR, "当前用户未使用微信注册");//请注册后使用微信登录
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_NOT_FOUNT_OPEN_ID);
			map.put(Constants.MESSAGE_STR, "缺少参数:OPENID,无法获取微信授权的openid");
		}
		
		return map;
	}
	
	/**
	 * 微信注册
	* @Title: weixinRegister 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年8月7日 下午1:38:26 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/weixinRegister")
	public @ResponseBody Map<String,Object> weixinRegister(
			LotterybuyerOrExpertDTO dto,
			@RequestParam(value = "wxHeadImgUrl",required = false) String wxHeadImgUrl,
			HttpServletRequest request,HttpSession httpSession) throws Exception
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(null != dto.getWxOpenId()&&!"".equals(dto.getWxOpenId()))
		{
			LotterybuyerOrExpert entity = lotterybuyerOrExpertService.getLotterybuyerOrExpertByTelephone(dto.getTelephone());
			if(null != entity)
			{
				entity.setWxOpenId(dto.getWxOpenId());
				entity.setModify(entity.getId());
				entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
				lotterybuyerOrExpertService.update(entity);
				dto = lotterybuyerOrExpertService.toDTO(entity);
				dto.setUserToken(TokenUtil.generateToken(dto.getTelephone(), dto.getPassword()));
				map.put("userDto",dto);
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE_OF_BIND_WEIXIN);
				map.put(Constants.MESSAGE_STR, "微信绑定成功");
			}
			else
			{
				entity = new LotterybuyerOrExpert();
				dto.setHandSel(new BigDecimal(0));
				dto.setColorCoins(new BigDecimal(0));
				dto.setAlreadyLogin(0);//注册后还没用登录
				BeanUtil.copyBeanProperties(entity, dto);
				entity.setId(UUID.randomUUID().toString());
				//对密码进行加密
				entity.setPassword(MyMD5Util.getEncryptedPwd(dto.getPassword()));
				//TODO:下载微信头像
				StringBuffer imguri = new StringBuffer();//头像uri（注册时默认不进行头像的上传）
				Uploadfile uploadfile = uploadfileService.downloadFileFromURL(wxHeadImgUrl, UUID.randomUUID().toString(), request);
				//绑定新头像
				if(null != uploadfile)
				{
					entity.setTouXiang(uploadfile.getNewsUuid());//关联新头像
				}
				//刷新融云用户信息,将图片信息同步
				imguri.append(OuterLotteryBuyerOrExpertController.DOMAIN)
						.append(request.getContextPath()).append(uploadfile.getUploadfilepath()).append(uploadfile.getUploadRealName());
				//创建融云用户id
				String rongyunToken = rongyunImService.getUserToken(entity.getId(),
						entity.getName(), imguri.toString());
				entity.setToken(rongyunToken);
				
				entity.setIsPhone("1");//从app端走注册接口的一定是手机用户
				entity.setIsExpert("0");//注册时用户的默认身份是彩民
				entity.setIsVirtual("0");//是否为虚拟用户（虚拟用户是由公司来创建的，没有实际意义）
				entity.setIsRobot("0");//从app端注册的用户都不是机器人用户
				entity.setIsStationOwner("0");//在注册时默认都不是站主
				entity.setFromApp("1");//app注册入口进入则为app用户
				
				entity.setIsDeleted(Constants.IS_NOT_DELETED);
				entity.setCreator(entity.getId());
				entity.setCreateTime(new Timestamp((System.currentTimeMillis())));
				entity.setModify(entity.getId());
				entity.setModifyTime(new Timestamp((System.currentTimeMillis())));
				lotterybuyerOrExpertService.save(entity);
				dto = lotterybuyerOrExpertService.toDTO(entity);
				dto.setUserToken(TokenUtil.generateToken(dto.getTelephone(), dto.getPassword()));
				map.put("userDto",dto);
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				map.put(Constants.MESSAGE_STR, "微信注册成功");
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_NOT_FOUNT_OPEN_ID);
			map.put(Constants.MESSAGE_STR, "缺少参数:OPENID,无法获取微信授权的openid");
		}
		return map;
	}
	
}

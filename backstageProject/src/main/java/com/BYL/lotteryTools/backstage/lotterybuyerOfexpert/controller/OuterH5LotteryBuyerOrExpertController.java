package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.exception.GlobalOuterExceptionHandler;
import com.BYL.lotteryTools.common.service.UploadfileService;

/**
 * 
* @Description: H5接口
* @author banna
* @date 2017年3月14日 上午9:52:33
 */
@Controller
@RequestMapping("/outerH5")
public class OuterH5LotteryBuyerOrExpertController extends GlobalOuterExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(OuterH5LotteryBuyerOrExpertController.class);
	
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
	 * HTML5
	* @Title: getYanzhengmaForRegisterOfH5 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param telephone
	* @param @param httpSession
	* @param @param request
	* @param @param reponse    设定文件 
	* @author banna
	* @date 2017年6月29日 下午1:37:48 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getYanzhengmaForRegisterOfH5" , method = RequestMethod.GET)
	public void getYanzhengmaForRegisterOfH5(@RequestParam(value="telephone",required=false) String telephone,
			HttpSession httpSession,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ResultBean resultBean = lotterybuyerOrExpertService.getYanzhengmaForRegister(telephone);
		 String callback = request.getParameter("jsoncallback");
		 PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");//设置允许所有域名可以跨域
		out.write(callback+"([ { resultCode:\""+resultBean.getResultCode()+"\",message:\""+resultBean.getMessage()+"\"}])");
		out.flush();
		out.close();
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
	@RequestMapping(value="/checkYanzhengmaOfH5", method = RequestMethod.GET)
	public void checkYanzhengmaOfH5(
			@RequestParam(value = "yanzhengma",required = true) String yanzhengma,
			@RequestParam(value = "telephone",required = true) String telephone,
			HttpServletRequest request,HttpSession httpSession,HttpServletResponse response) throws Exception
	{
		Map<String,Object> result = lotterybuyerOrExpertService.checkYanzhengma(yanzhengma, telephone);
		 String callback = request.getParameter("jsoncallback");
		 PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		out.write(callback+"([ { resultCode:\""+result.get("resultCode")+"\",message:\""+result.get("message")+"\"}])");
		out.flush();
		out.close();
	}
	
	/**
	 * h5注册页面
	* @Title: saveFromAppOfH5 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotterybuyerOrExpertDTO
	* @param @param request
	* @param @param httpSession
	* @param @param response
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月29日 下午3:22:29 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value="/saveFromAppOfH5", method = RequestMethod.GET)
	public void saveFromAppOfH5(
			LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO,
			HttpServletRequest request,HttpSession httpSession,HttpServletResponse response) throws Exception
	{
		Map<String,Object> result = lotterybuyerOrExpertService.saveFromApp(lotterybuyerOrExpertDTO, request);
		 String callback = request.getParameter("jsoncallback");
		 PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		out.write(callback+"([ { resultCode:\""+result.get("resultCode")+"\",message:\""+result.get("message")+"\"}])");
		out.flush();
		out.close();
	}
	
	
	
	
}

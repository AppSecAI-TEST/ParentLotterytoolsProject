package com.BYL.lotteryTools.common.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.common.util.Constants;

/**
 * 接口全局异常捕捉类
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年5月31日 下午3:50:55
 */
@Controller
public abstract class GlobalOuterExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalOuterExceptionHandler.class);
	public static final String DEFAULT_ERROR_VIEW = "error";

	    @ExceptionHandler
	    public @ResponseBody Map<String,Object> exception(HttpServletRequest request, Exception e) {  
	          
	        // 根据不同的异常类型进行不同处理
	    	Map<String,Object> map = new HashMap<String, Object>();
	        String returnMsg ;
	        if(e instanceof SQLException) 
	        {
	        	returnMsg = "数据库异常!";
	        }
	        else 
	        	if(e instanceof NullPointerException)
	        	{
	        		returnMsg = "空指针异常!";
	        	}
	        	 else 
	 	        	if(e instanceof ClassNotFoundException)
	 	        	{
	 	        		returnMsg = "指定的类不存在异常!";
	 	        	}
	 	        	 else 
	 	 	        	if(e instanceof ArithmeticException)
	 	 	        	{
	 	 	        		returnMsg = "数学运算异常!";
	 	 	        	}
	 	 	        	else 
		 	 	        	if(e instanceof ArrayIndexOutOfBoundsException)
		 	 	        	{
		 	 	        		returnMsg = "数组下标越界异常!";
		 	 	        	}
		 	 	        	else 
			 	 	        	if(e instanceof IllegalArgumentException)
			 	 	        	{
			 	 	        		returnMsg = "方法的参数错误异常!";
			 	 	        	}
			 	 	        	else 
				 	 	        	if(e instanceof IllegalAccessException)
				 	 	        	{
				 	 	        		returnMsg = "没有访问权限异常!";
				 	 	        	}
	        else
	        {
	        	returnMsg = "服务器错误";
	        	
	        }
	        
	        //添加自己的异常处理逻辑，如日志记录　　　
	        LOG.error("全局异常捕捉 ，异常返回提示："+ returnMsg+"异常信息："+e.getMessage());
	        LOG.error("ERROR:", e);  
	        map.put(Constants.MESSAGE_STR, returnMsg);
	        map.put(Constants.FLAG_STR, false);
	        map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);//服务器异常错误
	        return map;
	    }  
	    
}

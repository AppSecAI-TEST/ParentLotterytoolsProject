package com.BYL.lotteryTools.backstage.outer.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.TokenUtil;

public class APIInterceptor extends HandlerInterceptorAdapter {
	@Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
          
//        String token = request.getParameter("userToken");  
        String token = request.getHeader("userToken");
        boolean flag = false;
        if(null != token &&!"".equals(token))
        {
        	flag = TokenUtil.checkToken(token);
            if(!flag)
            {
            	Map<String,Object> map = new HashMap<String, Object>();
            	map.put(Constants.FLAG_STR, false);
        		map.put(Constants.CODE_STR, Constants.TOKEN_IS_PASS_CODE);
        		map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
        		response.setContentType("text/html;charset=UTF-8");  
            	response.getWriter().print(JSONObject.fromObject(map));
            }
        }
        else
        {
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put(Constants.FLAG_STR, false);
    		map.put(Constants.CODE_STR, Constants.TOKEN_IS_NOT_EXIST_CODE);
    		map.put(Constants.MESSAGE_STR, "token值不存在!");
    		response.setContentType("text/html;charset=UTF-8");  
        	response.getWriter().print(JSONObject.fromObject(map));
        }
        
        return flag; 
    }  
  
    @Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {  
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {  
    	
    }  
}

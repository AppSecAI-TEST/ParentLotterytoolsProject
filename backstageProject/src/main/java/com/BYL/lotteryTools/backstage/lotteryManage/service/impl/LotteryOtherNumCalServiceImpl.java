package com.BYL.lotteryTools.backstage.lotteryManage.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryOtherNumCalService;
import com.alibaba.fastjson.JSONObject;


@Service("lotteryOtherNumCalService")
@Transactional(propagation = Propagation.REQUIRED)
public class LotteryOtherNumCalServiceImpl implements LotteryOtherNumCalService 
{
	
	/**
     * @Description: 计算11选5扩展表方法（12选5同）
     * @author songj@sdfcp.com
     * @date Feb 15, 2016 4:24:40 PM
     * @param issueId
     * @return
     */
   public Map<String,Object> getRecordByIssueId(String objects) {
		 Map<String,Object> result = new HashMap<String, Object>();
		   
		 JSONObject params = JSONObject.parseObject(objects);
	     if(params.size()>0)
	     {
	    	 int oneInt = Integer.parseInt(params.get("no1").toString());
	         int twoInt =  Integer.parseInt(params.get("no2").toString());
	         int threeInt = Integer.parseInt(params.get("no3").toString());
	         int fourInt = Integer.parseInt(params.get("no4").toString());
	         int fiveInt = Integer.parseInt(params.get("no5").toString());
	         
	         //要计算的其他值
	         int threeSpan=0,
	  		   threeSum=0,
	  		   fiveSpan,
	  		   fiveSum,
	  		   oddNumber=0,
	  		   bigCount=0;
	         
	         threeSum = oneInt + twoInt+threeInt;
	         
	         fiveSum = oneInt + twoInt + threeInt + fourInt + fiveInt;
	         
	         int three[] = {oneInt,twoInt,threeInt};
	         
	         int five[] = {oneInt,twoInt,threeInt,fourInt,fiveInt};
	         
	         for(int i = 0;i < five.length;i++)
	         {
	             if(five[i]%2 != 0)
	             {
	                 oddNumber++;
	             }
	             if(five[i] > 6)
	             {
	                 bigCount++;
	             }
	         }
	         Arrays.sort(three);//对前三值排序
	         threeSpan = three[2] - three[0];//计算前三差值
	         Arrays.sort(five);//对前三值排序
	         fiveSpan = five[4] - five[0];//计算前五差值
	         
	         
	         result.put("ODD_COUNT", oddNumber);
	         result.put("BIG_COUNT", bigCount);
	         result.put("THREE_SPAN", threeSpan);
	         result.put("FIVE_SPAN", fiveSpan);
	         result.put("THREE_SUM", threeSum);
	         result.put("FIVE_SUM", fiveSum);
	         Arrays.sort(five);
	         result.put("SMALLEST_NUM", five[0]);
	         result.put("SMALLER_NUM", five[1]);
	         result.put("MIDDLE_NUM", five[2]);
	         result.put("BIGGER_NUM", five[3]);
	         result.put("BIGGEST_NUM", five[4]);
	         StringBuffer noArr = new StringBuffer();
	         for (int num : five) 
			    {
			    	noArr.append(this.translate(num));
				}
	         result.put("NOARR", noArr);
	         result.put("ORIGIN", "9");//9：公众号补录
	     }
	   
      
       return result;
   }
   
   private static String translate(int temp)
	  {
	    String rtn = null;
	    if (temp < 10) {
	      rtn = temp+"";
	    } else if (temp == 10) {
	      rtn = "A";
	    } else if (temp == 11) {
	      rtn = "J";
	    } else if (temp == 12) {
	      rtn = "Q";
	    }
	    return rtn;
	  }

   /**
    * 计算安徽快3扩展内容程序
    * @param srcDataBean
    * @return
    */
	public Map<String, Object> caluExtentInfo(String objects) {
		 Map<String,Object> result = new HashMap<String, Object>();
		   
		 JSONObject params = JSONObject.parseObject(objects);
	     if(params.size()>0)
	     {
	    	 int oneInt = Integer.parseInt(params.get("no1").toString());
		     int twoInt = Integer.parseInt(params.get("no2").toString());
		     int threeInt = Integer.parseInt(params.get("no3").toString());
		     
		     int threeSpan=0,
					   threeSum=0,
					   bigNumber = 0,
					   smallNumber=0,
					   oddNumber=0,
					   evenNumber =0,
					   noStatus = 0,
					   bigCount=0,
					   smallCount=0;
			     
			     
			     threeSum = oneInt + twoInt+threeInt;
			     
			     int b[] = {oneInt,twoInt,threeInt};
			     
			     for(int i=0;i<b.length;i++)
			     {
			         if(b[i] <= 3)
			         {
			             smallCount++;
			         }
			         else
			         {
			             bigCount++;
			         }
			     }
			     
			     Arrays.sort(b);
			     
			     bigNumber  = b[b.length-1];
			     
			     smallNumber =  b[0];
			     
			     threeSpan = bigNumber - smallNumber;
			     
			     if(oneInt%2 == 0)
			     {
			         evenNumber++;
			     }
			     else
			     {
			         oddNumber++;
			     }
			     if(twoInt%2 == 0)
			     {
			         evenNumber++;
			     }
			     else
			     {
			         oddNumber++;
			     }
			     if(threeInt%2 == 0)
			     {
			         evenNumber++;
			     }
			     else
			     {
			         oddNumber++;
			     }
			     if(oneInt != twoInt && twoInt != threeInt && oneInt != threeInt)
			     {
			         noStatus = 3;
			     }
			     else 
			  	   if(oneInt == twoInt && twoInt == threeInt && oneInt == threeInt)
			  	   {
			            noStatus = 1;
			  	   }
			  	   else
			  	   {
			  		   noStatus = 2;
			  	   }
			     
			     result.put("BIG_COUNT", bigCount);
			     result.put("EVEN_COUNT", evenNumber);
			     result.put("NUM_STATUS", noStatus);
			     result.put("ODD_COUNT", oddNumber);
			     result.put("SMALL_COUNT", smallCount);
			     result.put("THREE_SPAN", threeSpan);
			     result.put("THREE_SUM", threeSum);
			     result.put("ORIGIN", "9");//9：公众号补录
	     }
	     
	     
	   
	     return result;
	}
	
	 public Map<String,Object> otherLotteryPlay(String objects)
	 {
		 Map<String,Object> result = new HashMap<String, Object>();
		 
		 result.put("ORIGIN", "9");//9：公众号补录
		 
		   return result;
	 }
}

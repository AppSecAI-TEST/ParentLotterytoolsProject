package com.BYL.lotteryTools.backstage.outer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.app.controller.AppController;
import com.BYL.lotteryTools.backstage.app.dto.AppversionDTO;
import com.BYL.lotteryTools.backstage.app.entity.App;
import com.BYL.lotteryTools.backstage.app.entity.Appversion;
import com.BYL.lotteryTools.backstage.app.service.AppService;
import com.BYL.lotteryTools.backstage.app.service.AppversionService;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.SysMessage;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.SysMessageService;
import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryPlayService;
import com.BYL.lotteryTools.backstage.outer.dto.LotteryPlayOfProvince;
import com.BYL.lotteryTools.backstage.outer.dto.TransferDTO;
import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.outer.service.OuterInterfaceService;
import com.BYL.lotteryTools.backstage.prediction.entity.PredictionType;
import com.BYL.lotteryTools.backstage.prediction.service.PredictionTypeService;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.backstage.user.service.RegionService;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.exception.GlobalOuterExceptionHandler;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.TokenUtil;

@Controller
@RequestMapping("/outer")
public class OuterInterfaceController extends GlobalOuterExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(OuterInterfaceController.class);
	
	
	@Autowired
	private LotteryPlayService lotteryPlayService;
	
	@Autowired
	private OuterInterfaceService outerInterfaceService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private PredictionTypeService predictionTypeService;
	
	@Autowired
	private SysMessageService sysMessageService;
	
	@Autowired
	private AppversionService appversionService;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private AppService appService;
	
	
	private static final String NO5 = "5";
	
	private static final String NO3 = "3";
	
	public static int FiveInEle_QianErZhi = 110;//11选5前二直选理论周期
	
	public static int FiveInEle_QianErZuXuan = 55;//11选5前二组选理论周期
	
	public static int FiveInEle_QianErZuXuanSanma = 18;//11选5前二组选三码理论周期
	
	public static int FiveInEle_QianErZuXuanSima = 9;//11选5前二组选四码理论周期
	
	public static int FiveInEle_QianErZuXuanWuma = 6;//11选5前二组选五码理论周期
	
	public static int FiveInEle_QianErZuXuanLiuma = 4;//11选5前二组选六码理论周期
	
	public static int FiveInEle_QianErZuXuanQima = 2;//11选5前二组选七码理论周期
	
	public static int FiveInEle_QianSanZhi = 990;//11选5前三直选理论周期
	
	public static int FiveInEle_QianSanZuXuan = 165;//11选5前三组选理论周期
	
	public static int FiveInEle_QianSanSiMa = 41;//11选5前三四码理论周期
	
	public static int FiveInEle_QianSanWuMa = 17;//11选5前三四码理论周期
	
	public static int FiveInEle_QianSanLiuMa = 8;//11选5前三六码理论周期
	
	public static int FiveInEle_RenEr = 6;//11选5任二理论周期
	
	public static int FiveInEle_RenSan = 17;//11选5任三理论周期
	
	public static int FiveInEle_RenSanSi = 4;//11选5任三四理论周期
	
	public static int FiveInEle_RenSi = 66;//11选5任四理论周期
	
	public static int FiveInEle_RenSiWuma = 13;//11选5任四五码理论周期
	
	public static int FiveInEle_RenSiLiuma = 4;//11选5任四六码理论周期
	
	public static int FiveInEle_RenWu = 462;//11选5任五理论周期
	
	public static int FiveInEle_RenLiu = 77;//11选5任六理论周期
	
	public static int FiveInEle_RenQi = 22;//11选5任七理论周期
	
	public static int FiveInEle_RenBa = 9;//11选5任八理论周期
	
	//12选5的理论周期
	public static int FiveInTwe_QianErZhi = 132;//12选5前二直选理论周期
	
	public static int FiveInTwe_QianErZuXuan = 66;//12选5前二组选理论周期
	
	public static int FiveInTwe_QianErZuXuanSanma = 22;//12选5前二组选三码理论周期
	
	public static int FiveInTwe_QianErZuXuanSima = 11;//12选5前二组选四码理论周期
	
	public static int FiveInTwe_QianErZuXuanWuma = 7;//12选5前二组选五码理论周期
	
	public static int FiveInTwe_QianErZuXuanLiuma = 6;//12选5前二组选六码理论周期
	
	public static int FiveInTwe_QianErZuXuanQima = 3;//12选5前二组选七码理论周期
	
	public static int FiveInTwe = 2;//11选5前二组选七码理论周期
	
	public static int FiveInTwe_QianSanZhi = 1320;//12选5前三直选理论周期
	
	public static int FiveInTwe_QianSanZuXuan = 220;//12选5前三组选理论周期
	
	public static int FiveInTwe_QianSanSiMa = 55;//12选5前三四码理论周期
	
	public static int FiveInTwe_QianSanWuMa = 22;//12选5前三五码理论周期
	
	public static int FiveInTwe_QianSanLiuMa = 11;//12选5前三六码理论周期
	
	public static int FiveInTwe_RenEr = 7;//12选5任二理论周期
	
	public static int FiveInTwe_RenSan = 22;//12选5任三理论周期
	
	public static int FiveInTwe_RenSanSi = 5;//12选5任三四理论周期
	
	public static int FiveInTwe_RenSi = 99;//12选5任四理论周期
	
	public static int FiveInTwe_RenSiWuma = 20;//12选5任四五码理论周期
	
	public static int FiveInTwe_RenSiLiuma = 6;//12选5任四六码理论周期
	
	public static int FiveInTwe_RenWu = 792;//12选5任五理论周期
	
	public static int FiveInTwe_RenLiu = 132;//12选5任六理论周期
	
	public static int FiveInTwe_RenQi = 38;//12选5任七理论周期
	
	public static int FiveInTwe_RenBa = 15;//12选5任八理论周期
	
	/**
	 * 
	* @Title: getLotteryList 
	* @Description: TODO(获取开奖数据:包括走势图数据加载，上拉数据加载，下拉数据加载)
	* @param @param lotteryTypeId
	* @param @param provinceCode
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月12日 上午11:29:24 
	* @return List<SrcFiveDataBean>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getLotteryList",method=RequestMethod.GET)
	public @ResponseBody List<?> getLotteryList(@RequestParam(value="id",required=true) String lotteryTypeId,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="provinceCode",required=false) String provinceCode,
			@RequestParam(value="maxIssueId",required=false) String maxIssueId,
			@RequestParam(value="minIssueId",required=false) String minIssueId)
	{
		List<?> list = new ArrayList<SrcfivedataDTO>();
		
		//若为获取上拉/下拉数据，则加载40条
		
		//若为获取开奖数据，默认300条
		
		LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(lotteryTypeId);
		
		if(null != lotteryPlay)
		{
			String tbName = lotteryPlay.getCorrespondingTable();
			
			if("5".equals(lotteryPlay.getLotteryNumber()))
			{//获取5个开奖号码的开奖数据
				list = outerInterfaceService.getLotteryList(tbName, maxIssueId, minIssueId);
			}
			else
				if("3".equals(lotteryPlay.getLotteryNumber()))
				{//获取3个开奖号码的开奖数据
					list = outerInterfaceService.getLotteryListOfThree(tbName, maxIssueId, minIssueId);
				}
			
		}
		
		
		
		
		
		return list;
	}
	
	/**
	 * 
	* @Title: getLotteryPlayListOfProvince 
	* @Description: TODO(根据省份获取当前省份可以查看的高频彩种走势图列表) --app两处使用：1.点击工具进入工具列表时2.点击切换省份时
	* @param @param lotteryTypeId
	* @param @param provinceCode
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月12日 上午11:32:01 
	* @return List<LotteryPlayDTO>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getLotteryPlayListOfProvince",method=RequestMethod.GET)
	public @ResponseBody List<LotteryPlayOfProvince> getLotteryPlayListOfProvince(
			@RequestParam(value="id",required=false) String lotteryTypeId,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="provinceCode",required=false) String provinceCode)
	{
		List<LotteryPlayOfProvince> list = new ArrayList<LotteryPlayOfProvince>();
		
		
		list = outerInterfaceService.getLotteryPlayOfProvince(provinceCode);
		
		
		return list;
	}
	
	/**
	 * 
	* @Title: getProvinceOfZST 
	* @Description:获取当前后台中维护的所有区域彩种的区域并集列表
	* @param @param lotteryBulufanganId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月12日 上午11:34:35 
	* @return List<Province>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getProvinceOfZST",method=RequestMethod.GET)
	public @ResponseBody List<Province> getProvinceOfZST(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="id",required=false) String id)
	{
		List<Province> list = new ArrayList<Province>();
		
		list = outerInterfaceService.getLotteryPlayListOfProvince();
		
		return list;
	}
	
	
	
	//专家预测接口1：根据省份获取区域彩种列表
	@RequestMapping(value="/getPredictiontypeOfProvince",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getPredictiontypeOfProvince(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="provinceCode",required=false) String provinceCode)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<LotteryPlay> list = lotteryPlayService.getLotteryPlayByProvince(provinceCode);
		
		List<LotteryPlayDTO> dtos = lotteryPlayService.toRDTOS(list);
		
		map.put("lotteryPlays", dtos);
		map.put(Constants.FLAG_STR, true);
		map.put(Constants.TOKEN_FLAG_STR, true);
		map.put(Constants.MESSAGE_STR, "获取成功");
		
		return map;
	}
	
	
	//专家预测接口2：返回基本预测类型表（初始化1~6的基本预测类型）
	@RequestMapping(value="/getBaseInitPredictionType",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getBaseInitPredictionType(
			@RequestParam(value="userToken",required=false) String userToken//用户token
			)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		
		/**
		 * <option value="1">前三胆杀</option>
			<option value="2">任胆杀</option>
			<option value="3">前三六码复式</option>
			<option value="4">乐选4期计划</option>
			<option value="5">两码三期计划</option>
			<option value="6">任三精选6组</option>
		 */
		 if(null != userToken && !"".equals(userToken))
			{
				//校验token是否相同
				boolean tokenFlag = TokenUtil.checkToken(userToken);
				if(!tokenFlag)
				{//token不相同
					map.put(Constants.FLAG_STR, false);
					map.put(Constants.TOKEN_FLAG_STR, false);
					map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
				}
				else
				{
					List<TransferDTO> dtos = new ArrayList<TransferDTO>();
					TransferDTO d1 = new TransferDTO();
					d1.setName("前三胆杀");
					d1.setValue("1");
					dtos.add(d1);
					
					TransferDTO d2 = new TransferDTO();
					d2.setName("任胆杀");
					d2.setValue("2");
					dtos.add(d2);
					
					TransferDTO d3 = new TransferDTO();
					d3.setName("前三六码复式");
					d3.setValue("3");
					dtos.add(d3);
					
					TransferDTO d4 = new TransferDTO();
					d4.setName("乐选4期计划");
					d4.setValue("4");
					dtos.add(d4);
					
					TransferDTO d5 = new TransferDTO();
					d5.setName("两码三期计划");
					d5.setValue("5");
					dtos.add(d5);
					
					TransferDTO d6 = new TransferDTO();
					d6.setName("任三精选6组");
					d6.setValue("6");
					dtos.add(d6);
					
					map.put("baseDtos", dtos);
					map.put(Constants.FLAG_STR, true);
					map.put(Constants.TOKEN_FLAG_STR, true);
					map.put(Constants.MESSAGE_STR, "获取成功");
				}
			}
		 else
		 {
			 map.put(Constants.FLAG_STR, false);
			 map.put(Constants.TOKEN_FLAG_STR, false);
			 map.put(Constants.MESSAGE_STR, "token值不存在!");
		 }
	
		
		return map;
	}
	
	//专家预测接口3：根据区域彩种+基本预测类型（根据初始化基本预测类型找出预测方案表），在预测方案表中根据免费/收费筛选专家（按准确率排序）
	@RequestMapping(value="/getOrderExpertList",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getOrderExpertList(
//			@RequestParam(value="provinceCode",required=false) String provinceCode,
			@RequestParam(value="lotteryPlayId",required=false) String lotteryPlayId,
			@RequestParam(value="baseTypeId",required=false) String baseTypeId,//6种预测类型的id
			@RequestParam(value="isFree",required=false) String isFree,//0:免费 1：付费
			@RequestParam(value="page",required=false) String page,//当前是第几次获取
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="count",required=false) String count)//需要的专家数量
	{
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != userToken && !"".equals(userToken))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(userToken);
			if(!tokenFlag)
			{//token不相同
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.TOKEN_FLAG_STR, false);
				map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
			}
			else
			{
				//找出区域预测方案表的表名，然后在表内按顺序查询专家的列表
				List<PredictionType> plist = predictionTypeService.getPredictionTypeOfProAndLplay(lotteryPlayId, baseTypeId);
				String predictionTbname = "";//区域预测方案表
				if(null != plist && plist.size()>0)
				{
					predictionTbname = plist.get(0).getPredictionTable();
				}
				//获取当前开出的最大期号,并计算预测的期号
				String maxIssueId = lotteryPlayService.getYuceMaxIssueId(lotteryPlayId);
				
				//查询专家数据
				List<?> preOfExperts = predictionTypeService.getPredictionPlanOfExperts(maxIssueId, isFree, count, baseTypeId,predictionTbname);
				
				map.put("preOfExperts", preOfExperts);
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.TOKEN_FLAG_STR, true);
				map.put(Constants.MESSAGE_STR, "获取成功");
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.TOKEN_FLAG_STR, false);
			map.put(Constants.MESSAGE_STR, "token值不存在!");
		}
		return map;
	}
	
	//TODO:专家预测接口4：查看某个专家的预测（根据区域彩种和区域预测类型筛选专家）点击群内加号获取符合当前群属性的专家且点击专家查看详情时使用（群加号中使用）
	@RequestMapping(value="/getHistoryGroupPreOfExpert",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getHistoryGroupPreOfExpert(
			@RequestParam(value="expertId",required=false) String expertId,
			@RequestParam(value="lotteryType",required=false) String lotteryType,//体彩、福彩
			@RequestParam(value="baseTypeId",required=false) String baseTypeId,//6种预测类型的id
			@RequestParam(value="provinceCode",required=false) String provinceCode,//6种预测类型的id
			@RequestParam(value="page",required=false) String page,//当前是第几次获取
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="count",required=false) String count)//需要的专家数量
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(null != userToken && !"".equals(userToken))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(userToken);
			if(!tokenFlag)
			{//token不相同
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.TOKEN_FLAG_STR, false);
				map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
			}
			else
			{
				//TODO:根据省和体彩/福彩得到当前专家预测的区域彩种，然后根据区域彩种获取每个区域预测类型（将区域预测方案表去重）根据默认选择的基本预测类型分类id去查找预测数据
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.TOKEN_FLAG_STR, false);
			map.put(Constants.MESSAGE_STR, "token值不存在!");
		}
		
		
		return map;
	}
	
	
	//专家预测接口5：查看某个专家的某个区域预测类型的预测结果(在首页专家列表中点击专家查看详情时使用，且在页内切换不同基本预测类型分类时使用)
	@RequestMapping(value="/getHistoryPreOfExpert",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getHistoryPreOfExpert(
			@RequestParam(value="expertId",required=false) String expertId,
			@RequestParam(value="lotteryPlayId",required=false) String lotteryPlayId,
			@RequestParam(value="baseTypeId",required=false) String baseTypeId,//6种预测类型的id
			@RequestParam(value="page",required=false) String page,//当前是第几次获取
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="count",required=false) String count)//需要的专家数量
	{
		Map<String,Object> map = new HashMap<String, Object>();
		if(null != userToken && !"".equals(userToken))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(userToken);
			if(!tokenFlag)
			{//token不相同
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.TOKEN_FLAG_STR, false);
				map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
			}
			else
			{
				//找出区域预测方案表的表名
				List<PredictionType> plist = predictionTypeService.getPredictionTypeOfProAndLplay(lotteryPlayId, baseTypeId);
				String predictionTbname = "";//区域预测方案表
				if(null != plist && plist.size()>0)
				{
					predictionTbname = plist.get(0).getPredictionTable();
				}
				
				//查询当前专家的预测数据
				List<?> preOfExperts = predictionTypeService.getHisPredictionOfExpert(count, baseTypeId,predictionTbname,expertId);
						
				map.put("preOfExperts", preOfExperts);
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.TOKEN_FLAG_STR, true);
				map.put(Constants.MESSAGE_STR, "获取成功");
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.TOKEN_FLAG_STR, false);
			map.put(Constants.MESSAGE_STR, "token值不存在!");
		}
		return map;
	}
	
	/**
	 * 获取11选5的遗漏数据
	* @Title: getFiveNumberMissAnalysisData 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotteryPlayId
	* @param @param type:遗漏类型1：前三直选2：组选相关3：前二直选4：前二组5：任三相关6：任四相关7：其他任选
	* @param @param selectnum：号码个数
	* @param @param groupnumarr
	* @param @param orderby
	* @param @param ascOrDesc
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月22日 下午5:01:27 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getFiveNumberMissAnalysisData", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getFiveNumberMissAnalysisData(
			@RequestParam(value="lotteryPlayId",required=false)String lotteryPlayId,
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="selectnum",required=false)String selectnum,
			@RequestParam(value="groupnum",required=false)String[] groupnumarr,
			@RequestParam(value="orderby",required=false)String orderby,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="ascOrDesc",required=false)String ascOrDesc)
	{
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(lotteryPlayId);//获取补录信息数据，使用这个数据中的表名和开奖数字个数的信息
		
		String tableName = lotteryPlay.getCorrespondingTable();//获取关联表
		
		/*String kjNum = lotteryPlay.getLotteryNumber();//获取开奖字数个数
		
		String lotteryType = lotteryPlay.getLotteryType();//获取彩种（体彩/福彩）
		
		String province = lotteryPlay.getProvince();//获取省份
*/		
		String[] tableNameStart = tableName.split("_");
		
		String endNumber = lotteryPlay.getLotteryPlayBulufangan().getEndNumber();
		
		String yilouTableEnd = "MISSANALYSIS";
		//0：T 1：省份 2：玩法名称
		tableName = tableNameStart[0] + "_" + tableNameStart[1] + "_" + tableNameStart[2] + "_" + yilouTableEnd; 
		
		if("5".equals(lotteryPlay.getLotteryNumber()))
		{//11选5和12选5的遗漏查询
			String groupnum = "";
			if(null != groupnumarr)
			{
				for (String number : groupnumarr) 
				{
					if(number.equals("10"))
					{
						groupnum = groupnum + "A";
					}
					else
						if(number.equals("11"))
						{
							groupnum = groupnum + "J";
						}
						else
							if(number.equals("12"))
							{
								groupnum = groupnum + "Q";
							}
							else{
								groupnum = groupnum + number;
							}
				}
			}
			
			
			returnMap = outerInterfaceService.getMissAnalysisData(type, selectnum, groupnum, tableName,orderby,endNumber,ascOrDesc);
			
			
		}
		else
			if("3".equals(lotteryPlay.getLotteryNumber()))
			{//TODO:快三遗漏查询
				
				
			}
		
		
		return returnMap;
	}
	
	/**
	 * 根据推送目标获取系统消息
	* @Title: getSysMessageForTarget 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param telephone
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月5日 上午11:15:32 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getSysMessageForTarget", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getSysMessageForTarget(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="telephone",required=false)String telephone)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		if(null != userToken && !"".equals(userToken))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(userToken);
			if(!tokenFlag)
			{//token不相同
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.TOKEN_FLAG_STR, false);
				map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
			}
			else
			{
				List<SysMessage> list = sysMessageService.getSysMessageByTarget(telephone);
				
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.TOKEN_FLAG_STR, true);
				map.put(Constants.MESSAGE_STR, "获取成功");
				map.put("sysMessage", list);
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.TOKEN_FLAG_STR, false);
			map.put(Constants.MESSAGE_STR, "token值不存在!");
		}
		return map;
	}
	
	/**
	 * 获取当前应用的最新版本
	* @Title: getAppVersionOfApp 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param appId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月19日 下午2:51:03 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getAppVersionOfApp", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getAppVersionOfApp(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="appName",required=false)String appName)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		if(null != userToken && !"".equals(userToken))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(userToken);
			if(!tokenFlag)
			{//token不相同
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.TOKEN_FLAG_STR, false);
				map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
			}
			else
			{
				AppversionDTO dto = new AppversionDTO();
				if(null != appName && !"".equals(appName))
				{
					App app = appService.getAppByAppName(appName);
					if(null != app)
					{
						//获取当前appId下的且已上架的应用版本的最新版本数据
						Integer maxVersionFlowId =
								appversionService.
								findMaxVersionFlowId(app.getId(), AppController.APP_V_STATUS_SJ);//获取当前应用下的应用版本数据是上架状态的最大版本流水号
						if(null!=maxVersionFlowId)
						{
							Appversion appversion = appversionService.
									getAppversionByAppIdAndVersionFlowId(app.getId(), maxVersionFlowId);//根据appId和版本流水号获取应用版本数据
							
							Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(appversion.getAppVersionUrl());
							
							String apkUrl = "";
							if(null!=uploadfile)
							{
								apkUrl = uploadfile.getUploadfilepath()+uploadfile.getUploadRealName();//抓取附件的真实存储路径
								appversion.setAppVersionUrl(apkUrl);
							}
							dto = appversionService.toDTO(appversion);
							
							map.put("appversion", dto);
							map.put(Constants.FLAG_STR, true);
							map.put(Constants.TOKEN_FLAG_STR, true);
							map.put(Constants.MESSAGE_STR, "获取成功");
						}
						else
						{
							map.put(Constants.FLAG_STR, false);
							map.put(Constants.TOKEN_FLAG_STR, true);
							map.put(Constants.MESSAGE_STR, "获取失败,当前应用没有最新版本");
						}
					
					}
					else
					{
						LOG.error("getAppversionsOfStationCouldUse：当前应用的已发布的应用版本的版本好是null;应用name="+appName);
						map.put(Constants.FLAG_STR, false);
						map.put(Constants.TOKEN_FLAG_STR, true);
						map.put(Constants.MESSAGE_STR, "获取失败,应用名为所传参数的应用不存在");
					}
					
				}
			}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.TOKEN_FLAG_STR, false);
			map.put(Constants.MESSAGE_STR, "token值不存在!");
		}
		return map;
	}
	
	
	/**
	 * 根据省份表生成安卓需要的地区结构文件
	* @Title: generate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @author banna
	* @date 2017年4月12日 下午3:56:32 
	* @return void    返回类型 
	* @throws
	 */
	/*@RequestMapping(value="/generate",method=RequestMethod.GET)
	public void  generate()
	{
		List<Province> provinces = provinceService.findAll();
		
		for (Province province : provinces)
		{
			System.out.println("<province name='"+province.getPname()+"' pcode='"+province.getPcode()+"'>");
			List<City> cities = cityService.findCitiesOfProvice(province.getPcode());
			for (City city : cities) 
			{
				System.out.println("<city name='"+city.getCname()+"' ccode='"+city.getCcode()+"'>");
				List<Region> regions = regionService.findRegionsOfCity(city.getCcode());
				
				for (Region region : regions) 
				{
					System.out.println("<district name='"+region.getAname()+"' rcode='"+region.getAcode()+"'/>");
				}
				
				System.out.println("</city >");
			}
			System.out.println("</province>");
		}
	}*/
	
	
}

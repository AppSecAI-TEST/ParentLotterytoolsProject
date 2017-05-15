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

@Controller
@RequestMapping("/outer")
public class OuterInterfaceController 
{
	private Logger logger = LoggerFactory.getLogger(OuterInterfaceController.class);
	
	
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
			@RequestParam(value="provinceCode",required=false) String provinceCode,
			@RequestParam(value="maxIssueId",required=false) String maxIssueId,
			@RequestParam(value="minIssueId",required=false) String minIssueId)
	{
		List<?> list = new ArrayList<SrcfivedataDTO>();
		
		//若为获取上拉/下拉数据，则加载40条
		
		//若为获取开奖数据，默认300条
		
		LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(lotteryTypeId);
		
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
			@RequestParam(value="id",required=false) String id)
	{
		List<Province> list = new ArrayList<Province>();
		
		list = outerInterfaceService.getLotteryPlayListOfProvince();
		
		return list;
	}
	
	
	
	//专家预测接口1：根据省份获取区域彩种列表
	@RequestMapping(value="/getPredictiontypeOfProvince",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getPredictiontypeOfProvince(
			@RequestParam(value="provinceCode",required=false) String provinceCode)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<LotteryPlay> list = lotteryPlayService.getLotteryPlayByProvince(provinceCode);
		
		List<LotteryPlayDTO> dtos = lotteryPlayService.toRDTOS(list);
		
		map.put("lotteryPlays", dtos);
		map.put("flag", true);
		map.put("message", "获取成功");
		
		return map;
	}
	
	
	//专家预测接口2：返回基本预测类型表（初始化1~6的基本预测类型）
	@RequestMapping(value="/getBaseInitPredictionType",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getBaseInitPredictionType()
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
		map.put("flag", true);
		map.put("message", "获取成功");
		
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
			@RequestParam(value="count",required=false) String count)//需要的专家数量
	{
		Map<String,Object> map = new HashMap<String,Object>();
		
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
		map.put("flag", true);
		map.put("message", "获取成功");
		
		
		return map;
	}
	
	//TODO:专家预测接口4：查看某个专家的预测（根据区域彩种和区域预测类型筛选专家）点击群内加号获取符合当前群属性的专家且点击专家查看详情时使用
	@RequestMapping(value="/getHistoryGroupPreOfExpert",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getHistoryGroupPreOfExpert(
			@RequestParam(value="expertId",required=false) String expertId,
			@RequestParam(value="lotteryType",required=false) String lotteryType,//体彩、福彩
			@RequestParam(value="baseTypeId",required=false) String baseTypeId,//6种预测类型的id
			@RequestParam(value="provinceCode",required=false) String provinceCode,//6种预测类型的id
			@RequestParam(value="page",required=false) String page,//当前是第几次获取
			@RequestParam(value="count",required=false) String count)//需要的专家数量
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		//根据省和体彩/福彩得到当前专家预测的区域彩种，然后根据区域彩种获取每个区域预测类型（将区域预测方案表去重）根据默认选择的基本预测类型分类id去查找预测数据
		
		
		
		return map;
	}
	
	
	//专家预测接口5：查看某个专家的某个区域预测类型的预测结果(在首页专家列表中点击专家查看详情时使用，且在页内切换不同基本预测类型分类时使用)
	@RequestMapping(value="/getHistoryPreOfExpert",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getHistoryPreOfExpert(
			@RequestParam(value="expertId",required=false) String expertId,
			@RequestParam(value="lotteryPlayId",required=false) String lotteryPlayId,
			@RequestParam(value="baseTypeId",required=false) String baseTypeId,//6种预测类型的id
			@RequestParam(value="page",required=false) String page,//当前是第几次获取
			@RequestParam(value="count",required=false) String count)//需要的专家数量
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
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
		map.put("flag", true);
		map.put("message", "获取成功");
		
		
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

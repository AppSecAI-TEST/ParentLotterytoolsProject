package com.BYL.lotteryTools.backstage.outer.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryPlayService;
import com.BYL.lotteryTools.backstage.outer.dto.LotteryPlayOfProvince;
import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.outer.service.OuterInterfaceService;
import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.entity.Region;
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
	public @ResponseBody List<SrcfivedataDTO> getLotteryList(@RequestParam(value="id",required=true) String lotteryTypeId,
			@RequestParam(value="provinceCode",required=false) String provinceCode,
			@RequestParam(value="maxIssueId",required=false) String maxIssueId,
			@RequestParam(value="minIssueId",required=false) String minIssueId)
	{
		List<SrcfivedataDTO> list = new ArrayList<SrcfivedataDTO>();
		
		//若为获取上拉/下拉数据，则加载40条
		
		//若为获取开奖数据，默认300条
		
		LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(lotteryTypeId);
		
		String tbName = lotteryPlay.getCorrespondingTable();
		
		list = outerInterfaceService.getLotteryList(tbName, maxIssueId, minIssueId);
		
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

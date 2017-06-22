package com.BYL.lotteryTools.backstage.user.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Region;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.backstage.user.service.RegionService;

@Controller
@RequestMapping("/area")
public class AreaController {
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private RegionService regionService;
	
	
	@RequestMapping(value = "/initCity", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> initCity(
			@RequestParam(value="province",required=false) String province,
			@RequestParam(value="cities",required=false) String[] cities,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String ccode = "";
		String cname = "";
		for (int i=0;i<cities.length;i++) {
			
			if(i%2!=0)
			{
				ccode = cities[i];
				City city = cityService.getCityByCcodeAndProvinceCode(ccode, province);
				if(null == city)
				{//当前城市不存在
					List<City> citylist = cityService.findAllCity();
					cname = cities[i-1];
					city = new City();
					city.setId((citylist.size()+1)+"");
					city.setCcode(ccode);
					city.setCname(cname);
					city.setProvinceCode(province);
					city.setCreateTime(new Timestamp(System.currentTimeMillis()));
					cityService.save(city);
				}
			}
			
		}
		
		return map;
	}
	
	@RequestMapping(value = "/initRegion", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> initRegion(
			@RequestParam(value="city",required=false) String city,
			@RequestParam(value="regions",required=false) String[] regions,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String acode = "";
		String aname = "";
		for (int i=0;i<regions.length;i++) {
			
			if(i%2!=0)
			{
				acode = regions[i];
				Region region = regionService.getRegionByAcodeAndCityCode(acode, city);
				if(null == region)
				{//当前城市不存在
					List<Region> regionlist = regionService.findAllRegion();
					aname = regions[i-1];
					System.out.println(aname+":"+acode+":"+city);
					region = new Region();
					region.setId((regionlist.size()+1)+"");
					region.setAcode(acode);
					region.setAname(aname);
					region.setCityCode(city);
					region.setCreateTime(new Timestamp(System.currentTimeMillis()));
					regionService.save(region);
				}
			}
			
		}
		
		return map;
	}
}

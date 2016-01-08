/*
 * @(#)AddressMainService.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.addressMaintenance;

import et.bo.addressMaintenance.service.AddressMainService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>省市县地址信息维护dwr调用</p>
 * 
 * @version 2009-03-04
 * @author wangwenquan
 */
public class AddressMainDwr {
	public AddressMainDwr()
	{
		service = (AddressMainService)SpringRunningContainer.getInstance().getBean("AddressMainService");
	}
	AddressMainService service;
	/**
	 * 字符串形式的城市集合 沈阳#辽阳
	 * @return "沈阳#辽阳"
	 */
	public String getAllCitys()
	{
		return service.getAllCitys();
	}
	/**
	 * 城市下属区县 和平区 兴城市 铁岭县
	 * @param cityName
	 * @return "和平区#兴城市#铁岭县"
	 */
	public String getAllSectionByCityName(String cityName)
	{
		return service.getAllSectionByCityName(cityName);
	}
	/**
	 * 区县 十四纬路街道办事处 砂山街道办事处 万福镇 蔡牛乡
	 * @param sectionName
	 * @return " 十四纬路街道办事#砂山街道办事处#万福镇#蔡牛乡" 
	 */
	public String getAllVillageATownsBySection(String sectionName)
	{
		return service.getAllVillageATownsBySection(sectionName);
	}
	/**
	 * 乡镇下的社区/村 五经街 孤家子 下潘庄子
	 * @param villageName
	 * @return "五经街#孤家子#下潘庄子"
	 */
	public String getAllCommunityByVillage(String villageName)
	{
		return service.getAllCommunityByVillage(villageName);
	}
    /**
     * 增加城市
     * @param cityName
     */
	public void addCity(String cityName,String num)
	{
		service.addCity(cityName, num);
	}
    /**
     * 删除城市
     * @param cityName
     */
	public void deleteCity(String cityName)
	{
		service.deleteCity(cityName);
	}
    /**
     * 修改城市
     * @param cityName
     */
	public void updateCity(String cityName,String srcName)
	{
		service.updateCity(cityName, srcName);
	}



	/**
	 * 增加区县
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void addArea(String cityName,String cityNum,String areaName, String areaNum)
	{
		if(cityName==null)
			cityName="";
		service.addArea(cityName, cityNum, areaName, areaNum);
	}
	/**
	 * 删除区县
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void deleteArea(String cityName,String cityNum,String areaName, String areaNum)
	{
		service.deleteArea(cityName, cityNum, areaName, areaNum);
	}
	/**
	 * 修改区县
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void updateArea(String cityName,String areaName, String srcName)
	{
		service.updateArea(cityName, areaName, srcName);
	}
	/**
	 * 删除乡镇
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void deleteTowns(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum)
	{
		service.deleteTowns(cityName, cityNum, areaName, areaNum,townName,townNum);
	}
	/**
	 * 增加乡镇
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void addTowns(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum)
	{
		if(cityName==null)
			cityName="";
		if(areaName==null)
			areaName="";
		service.addTowns(cityName, cityNum, areaName, areaNum, townName, townNum);
	}
	/**
	 * 修改乡镇
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void updateTowns(String cityName,String areaName,String townName, String srcName)
	{
		service.updateTowns(cityName,areaName,townName,srcName);
	}
	/**
	 * 增加村庄
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 * @param countyName
	 * @param countyNum
	 */
	public void addCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum)
	{
		if(cityName==null)
			cityName="";
		if(areaName==null)
			areaName="";
		if(townName==null)
			townName="";
		service.addCommunityAvillages(cityName, cityNum, areaName, areaNum,townName,townNum,countyName,countyNum);
	}
	/**
	 * 删除村庄
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 * @param countyName
	 * @param countyNum
	 */
	public void deleteCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum)
	{
		service.deleteCommunityAvillages(cityName, cityNum, areaName, areaNum,townName,townNum,countyName,countyNum);
	}
	/**
	 * 修改村庄
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 * @param countyName
	 * @param countyNum
	 */
	public void updateCommunityAvillages(String cityName,String areaName,String townName,String countyName,String srcName)
	{
		service.updateCommunityAvillages(cityName, areaName,townName,countyName,srcName);
	}
	
}

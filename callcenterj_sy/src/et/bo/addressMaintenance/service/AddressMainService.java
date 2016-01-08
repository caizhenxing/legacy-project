/*
 * @(#)AddressMainService.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.addressMaintenance.service;
/**
 * <p>省市县地址信息维护</p>
 * 
 * @version 2009-03-04
 * @author wangwenquan
 */
public interface AddressMainService {
	/**
	 * 字符串形式的城市集合 沈阳#辽阳
	 * @return "沈阳#辽阳"
	 */
	public String getAllCitys();
	/**
	 * 城市下属区县 和平区 兴城市 铁岭县
	 * @param cityName
	 * @return "和平区#兴城市#铁岭县"
	 */
	public String getAllSectionByCityName(String cityName);
	/**
	 * 区县 十四纬路街道办事处 砂山街道办事处 万福镇 蔡牛乡
	 * @param sectionName
	 * @return " 十四纬路街道办事#砂山街道办事处#万福镇#蔡牛乡" 
	 */
	public String getAllVillageATownsBySection(String sectionName);
	/**
	 * 乡镇下的社区/村 五经街 孤家子 下潘庄子
	 * @param villageName
	 * @return "五经街#孤家子#下潘庄子"
	 */
	public String getAllCommunityByVillage(String villageName);
    /**
     * 增加城市
     * @param cityName
     */
	public void addCity(String cityName,String num);
	 /**
     * 删除城市
     * @param cityName
     */
	public void deleteCity(String cityName);
    /**
     * 增加城市
     * @param cityName
     */
	public void updateCity(String cityName,String srcName);
	/**
	 * 增加区县
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void addArea(String cityName,String cityNum,String areaName, String areaNum);
	/**
	 * 删除区县
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void deleteArea(String cityName,String cityNum,String areaName, String areaNum);
	/**
	 * 增加乡镇
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void addTowns(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum);
	/**
	 * 修改区县
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void updateArea(String cityName,String areaName, String srcName);
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
	public void addCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum);
	
	/**
	 * 删除乡镇
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void deleteTowns(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum);
	/**
	 * 修改乡镇
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void updateTowns(String cityName,String areaName,String townName, String srcName);
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
	public void deleteCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum);
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
	public void updateCommunityAvillages(String cityName,String areaName,String townName,String countyName,String srcName);
}

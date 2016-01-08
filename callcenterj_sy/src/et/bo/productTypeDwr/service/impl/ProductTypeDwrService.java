package et.bo.productTypeDwr.service.impl;


import et.bo.productTypeDwr.service.ProductTypeService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>产品类别信息维护dwr调用</p>
 * 
 * @version 2009-03-11
 * @author lidan
 */
public class ProductTypeDwrService {
	ProductTypeService service;
	public ProductTypeDwrService()
	{
		//记得写spring文件并且把 下边的bean名改了。。。。。。。。。。。。
		service = (ProductTypeService)SpringRunningContainer.getInstance().getBean("ProductTypeService");
	}
	
	/**
	 * 字符串形式的产品大类集合 
	 * @return "大类1#大类2"
	 */
	public String getAllName()
	{
		return service.getAllName1();
	}
	/**
	 * 大类下的小类 
	 * @param 
	 * @return "小类1#小类2#小类3"
	 */
	public String getAllName2(String name1)
	{
		return service.getAllName2(name1);
	}
	/**
	 * 产品名称字符串
	 * @param 
	 * @return 
	 */
	public String getAllName3(String name2)
	{
		return service.getAllName3(name2);
	}
//	/**
//	 * 乡镇下的社区/村 五经街 孤家子 下潘庄子
//	 * @param villageName
//	 * @return "五经街#孤家子#下潘庄子"
//	 */
//	public String getAllCommunityByVillage(String villageName)
//	{
//		return service.getAllCommunityByVillage(villageName);
//	}
    /**
     * 增加大类
     * @param 
     */
	public void addName1(String name1)
	{
		service.addName1(name1);
	}
    /**
     * 删除城市
     * @param cityName
     */
	public void deleteName1(String name1)
	{
		service.deleteName1(name1);
	}
    /**
     * 修改城市
     * @param cityName
     */
	public void updateName1(String newName1,String oldName1)
	{
		service.updateName1(newName1, oldName1);
	}



	/**
	 * 增加小类
	 * 
	 */
	public void addName2(String name1,String name2)
	{
		if(name1==null)
			name1="";
		service.addName2(name1, name2);
	}
	/**
	 * 删除小类
	 */
	public void deleteName2(String name1,String name2)
	{
		service.deleteName2(name1, name2);
	}
	/**
	 * 修改小类
	 */
	public void updateName2(String name1,String newname2, String oldname2)
	{
		service.updateName2(name1, newname2, oldname2);
	}
	/**
	 * 删除产品名称
	 * 
	 */
	public void deleteName3(String name1,String name2,String name3)
	{
		service.deleteName3(name1, name2, name3);
	}
	/**
	 * 增加产品名称
	 */
	public void addName3(String name1,String name2, String name3)
	{
		if(name1==null)
			name1="";
		if(name2==null)
			name2="";
		service.addName3(name1, name2, name3);
	}
	/**
	 * 修改产品名称
	 */
	public void updateName3(String name1,String name2,String newname3, String oldname3)
	{
		service.updateName3(name1, name2, newname3, oldname3);
	}
//	/**
//	 * 增加村庄
//	 * @param cityName
//	 * @param cityNum
//	 * @param areaName
//	 * @param areaNum
//	 * @param townName
//	 * @param townNum
//	 * @param countyName
//	 * @param countyNum
//	 */
//	public void addCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum)
//	{
//		if(cityName==null)
//			cityName="";
//		if(areaName==null)
//			areaName="";
//		if(townName==null)
//			townName="";
//		service.addCommunityAvillages(cityName, cityNum, areaName, areaNum,townName,townNum,countyName,countyNum);
//	}
//	/**
//	 * 删除村庄
//	 * @param cityName
//	 * @param cityNum
//	 * @param areaName
//	 * @param areaNum
//	 * @param townName
//	 * @param townNum
//	 * @param countyName
//	 * @param countyNum
//	 */
//	public void deleteCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum)
//	{
//		service.deleteCommunityAvillages(cityName, cityNum, areaName, areaNum,townName,townNum,countyName,countyNum);
//	}
//	/**
//	 * 修改村庄
//	 * @param cityName
//	 * @param cityNum
//	 * @param areaName
//	 * @param areaNum
//	 * @param townName
//	 * @param townNum
//	 * @param countyName
//	 * @param countyNum
//	 */
//	public void updateCommunityAvillages(String cityName,String areaName,String townName,String countyName,String srcName)
//	{
//		service.updateCommunityAvillages(cityName, areaName,townName,countyName,srcName);
//	}
//	
}

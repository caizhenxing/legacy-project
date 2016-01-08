/*
 * @(#)AddressMainService.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.addressMaintenance;

import et.bo.addressMaintenance.service.AddressMainService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>ʡ���ص�ַ��Ϣά��dwr����</p>
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
	 * �ַ�����ʽ�ĳ��м��� ����#����
	 * @return "����#����"
	 */
	public String getAllCitys()
	{
		return service.getAllCitys();
	}
	/**
	 * ������������ ��ƽ�� �˳��� ������
	 * @param cityName
	 * @return "��ƽ��#�˳���#������"
	 */
	public String getAllSectionByCityName(String cityName)
	{
		return service.getAllSectionByCityName(cityName);
	}
	/**
	 * ���� ʮ��γ·�ֵ����´� ɰɽ�ֵ����´� ���� ��ţ��
	 * @param sectionName
	 * @return " ʮ��γ·�ֵ�����#ɰɽ�ֵ����´�#����#��ţ��" 
	 */
	public String getAllVillageATownsBySection(String sectionName)
	{
		return service.getAllVillageATownsBySection(sectionName);
	}
	/**
	 * �����µ�����/�� �徭�� �¼��� ����ׯ��
	 * @param villageName
	 * @return "�徭��#�¼���#����ׯ��"
	 */
	public String getAllCommunityByVillage(String villageName)
	{
		return service.getAllCommunityByVillage(villageName);
	}
    /**
     * ���ӳ���
     * @param cityName
     */
	public void addCity(String cityName,String num)
	{
		service.addCity(cityName, num);
	}
    /**
     * ɾ������
     * @param cityName
     */
	public void deleteCity(String cityName)
	{
		service.deleteCity(cityName);
	}
    /**
     * �޸ĳ���
     * @param cityName
     */
	public void updateCity(String cityName,String srcName)
	{
		service.updateCity(cityName, srcName);
	}



	/**
	 * ��������
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
	 * ɾ������
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
	 * �޸�����
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
	 * ɾ������
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
	 * ��������
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
	 * �޸�����
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
	 * ���Ӵ�ׯ
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
	 * ɾ����ׯ
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
	 * �޸Ĵ�ׯ
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

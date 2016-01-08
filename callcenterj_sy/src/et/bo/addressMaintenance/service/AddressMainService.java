/*
 * @(#)AddressMainService.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.addressMaintenance.service;
/**
 * <p>ʡ���ص�ַ��Ϣά��</p>
 * 
 * @version 2009-03-04
 * @author wangwenquan
 */
public interface AddressMainService {
	/**
	 * �ַ�����ʽ�ĳ��м��� ����#����
	 * @return "����#����"
	 */
	public String getAllCitys();
	/**
	 * ������������ ��ƽ�� �˳��� ������
	 * @param cityName
	 * @return "��ƽ��#�˳���#������"
	 */
	public String getAllSectionByCityName(String cityName);
	/**
	 * ���� ʮ��γ·�ֵ����´� ɰɽ�ֵ����´� ���� ��ţ��
	 * @param sectionName
	 * @return " ʮ��γ·�ֵ�����#ɰɽ�ֵ����´�#����#��ţ��" 
	 */
	public String getAllVillageATownsBySection(String sectionName);
	/**
	 * �����µ�����/�� �徭�� �¼��� ����ׯ��
	 * @param villageName
	 * @return "�徭��#�¼���#����ׯ��"
	 */
	public String getAllCommunityByVillage(String villageName);
    /**
     * ���ӳ���
     * @param cityName
     */
	public void addCity(String cityName,String num);
	 /**
     * ɾ������
     * @param cityName
     */
	public void deleteCity(String cityName);
    /**
     * ���ӳ���
     * @param cityName
     */
	public void updateCity(String cityName,String srcName);
	/**
	 * ��������
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void addArea(String cityName,String cityNum,String areaName, String areaNum);
	/**
	 * ɾ������
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void deleteArea(String cityName,String cityNum,String areaName, String areaNum);
	/**
	 * ��������
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void addTowns(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum);
	/**
	 * �޸�����
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void updateArea(String cityName,String areaName, String srcName);
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
	public void addCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum);
	
	/**
	 * ɾ������
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void deleteTowns(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum);
	/**
	 * �޸�����
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void updateTowns(String cityName,String areaName,String townName, String srcName);
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
	public void deleteCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum);
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
	public void updateCommunityAvillages(String cityName,String areaName,String townName,String countyName,String srcName);
}

package et.bo.productTypeDwr.service.impl;


import et.bo.productTypeDwr.service.ProductTypeService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>��Ʒ�����Ϣά��dwr����</p>
 * 
 * @version 2009-03-11
 * @author lidan
 */
public class ProductTypeDwrService {
	ProductTypeService service;
	public ProductTypeDwrService()
	{
		//�ǵ�дspring�ļ����Ұ� �±ߵ�bean�����ˡ�����������������������
		service = (ProductTypeService)SpringRunningContainer.getInstance().getBean("ProductTypeService");
	}
	
	/**
	 * �ַ�����ʽ�Ĳ�Ʒ���༯�� 
	 * @return "����1#����2"
	 */
	public String getAllName()
	{
		return service.getAllName1();
	}
	/**
	 * �����µ�С�� 
	 * @param 
	 * @return "С��1#С��2#С��3"
	 */
	public String getAllName2(String name1)
	{
		return service.getAllName2(name1);
	}
	/**
	 * ��Ʒ�����ַ���
	 * @param 
	 * @return 
	 */
	public String getAllName3(String name2)
	{
		return service.getAllName3(name2);
	}
//	/**
//	 * �����µ�����/�� �徭�� �¼��� ����ׯ��
//	 * @param villageName
//	 * @return "�徭��#�¼���#����ׯ��"
//	 */
//	public String getAllCommunityByVillage(String villageName)
//	{
//		return service.getAllCommunityByVillage(villageName);
//	}
    /**
     * ���Ӵ���
     * @param 
     */
	public void addName1(String name1)
	{
		service.addName1(name1);
	}
    /**
     * ɾ������
     * @param cityName
     */
	public void deleteName1(String name1)
	{
		service.deleteName1(name1);
	}
    /**
     * �޸ĳ���
     * @param cityName
     */
	public void updateName1(String newName1,String oldName1)
	{
		service.updateName1(newName1, oldName1);
	}



	/**
	 * ����С��
	 * 
	 */
	public void addName2(String name1,String name2)
	{
		if(name1==null)
			name1="";
		service.addName2(name1, name2);
	}
	/**
	 * ɾ��С��
	 */
	public void deleteName2(String name1,String name2)
	{
		service.deleteName2(name1, name2);
	}
	/**
	 * �޸�С��
	 */
	public void updateName2(String name1,String newname2, String oldname2)
	{
		service.updateName2(name1, newname2, oldname2);
	}
	/**
	 * ɾ����Ʒ����
	 * 
	 */
	public void deleteName3(String name1,String name2,String name3)
	{
		service.deleteName3(name1, name2, name3);
	}
	/**
	 * ���Ӳ�Ʒ����
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
	 * �޸Ĳ�Ʒ����
	 */
	public void updateName3(String name1,String name2,String newname3, String oldname3)
	{
		service.updateName3(name1, name2, newname3, oldname3);
	}
//	/**
//	 * ���Ӵ�ׯ
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
//	 * ɾ����ׯ
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
//	 * �޸Ĵ�ׯ
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

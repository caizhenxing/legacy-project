/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.caseinfo.casetype.service;

import java.util.List;
import java.util.Map;

/**
 * @describe ��������
 * @author 
 * @version 1.0, 200-03-04
 * @see
 */
public interface CaseTypeService{
	
	/**
	 * ���ش���
	 * @return
	 */
	public List loadBigType() ;
	
	/**
	 * ���ݴ����ȡС�� ��������ģ��
	 * @param bigType
	 * @return
	 */
	public Map getSmallTypeByBigType(String bigType);
	
	/**
	 * ���ݴ����ȡС��  ҳ��Ӧ��
	 * @param bigType
	 * @return
	 */
	public Map getSmallTypeByBigType_app(String bigType);
	
	/**
	 * ���ݴ������С�� ҳ�����list
	 * @param bigType
	 * @return
	 */
	public List loadSmallTypeByBigType(String bigType);
	
	/**
	 * ��Ӵ���
	 * @param bigType
	 * @return
	 */
	public Map addBigType(String bigType);
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public Map getBigType();
	
	/**
	 * ���С��
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public Map addSmallType(String bigType,String smallType);	
	
	/**
	 * �޸Ĵ���
	 * @param oldBigType
	 * @param newBigType
	 * @return
	 */
	public Map updateBigType(String oldBigType,String newBigType);
	
	/**
	 * ɾ������
	 * @param bigType
	 * @return
	 */
	public int deleteBigType(String bigType);
	
	/**
	 * �޸�С��
	 * @param id
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public Map updateSmallType(String id,String bigType,String smallType);
	
	/**
	 * ɾ��С��
	 * @param id
	 * @return
	 */
	public int deleteSmallType(String id);
	
}

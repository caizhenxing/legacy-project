/*
 * @(#)QuestionAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.screen.service;

import java.util.List;

import et.po.OperCustinfo;
import excellence.framework.base.container.SpringRunningContainer;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * <p>����Ļ�ṩ����</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public interface ScreenService {

	/**
	 * ÿ�ռ۸��ѯ��Ҫ ��Ʒ����(product_name) ���۲���(cust_addr) ��Ʒ�۸�(product_price/priduct_unit 8.00Ԫ/��) �۸�����(�۸�����)���ScreenPriceInfo����
	 * @return List
	 */
	public List getScreenPriceInfo();

	/**
	 * ����Ļ���ÿ�չ���ScreenSadInfo����ļ��� 
	 * @return List
	 */
	public List getScreenSadInfo();
	/**
	 * ��ý�䰸���б�
	 * @return  List
	 * @author wangwenquan
	 */
	public List<DynaBeanDTO> getCaseInfoList();
	/**
	 * 12316��Ѷ����
	 * @param title
	 * @param content
	 * @return void
	 */
	public void addQuickMessage(String title, String content);
	/**
	 * 12316��Ѷ�б�
	 * @param title
	 * @param content
	 */
	public List<DynaBeanDTO> quickMessageList();
	/**
	 * ����ʵ��ͳ��
	 * @return List
	 */
	public List<DynaBeanDTO> huaWuList();
	/**
	 * ����ũ���ƽ�
	 * @return  List
	 * @author wangwenquan
	 */
	/**
	 * �����ע
	 */
	public List<DynaBeanDTO> jiaoDianAnliList();
	//public List<OperCaseinfo> getCaseInfoList();
	/**
	 * ��ʽ���۸�
	 * @param String priceStr
	 * @param int decimalNum С�����λ
	 * @return formatStr
	 */
	public String formatPriceStr(String priceStr, int decimalNum);
	/**
	 * �õ���ѯ����
	 * return DynaBeanDTO
	 */
	public DynaBeanDTO getZiXunSumDtl();
	/**
	 * �õ�����ר��
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllExperts();
	/**
	 * �õ�����ר��
	 * @param type ����
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllExpertsByType(String type);
	/**
	 * �õ����л���ר��
	 * @param type ����
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllMutexExpertsByType(String type);
	/**
	 * ����ר������
	 * @param type ����
	 * @param ids ids
	 * @return List<OperCustinfo>
	 */
	public void updateScreenExpertType(String type, String ids);
	
	/**
	 * ��ʾ���һ���µ��ĸ��������绰�Ĵ����������������������ʾ����������
	 * @return
	 */
//	public List getCallLogStatisByMonth();
	
	/**
	 * ��ʾ���ǵ�����ĸ��������绰�Ĵ����������������������ʾ����������
	 * @return
	 */	
//	public List getCallLogStatisByDay();
	/**
	 * ��ʾ���һ���µ��ĸ��������绰�Ĵ����������������������ʾ����������
	 * ��ʾ���ǵ�����ĸ��������绰�Ĵ����������������������ʾ����������
	 */
	public void createXml();
	
}

/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-5-27
 */
package et.bo.output.service;

import java.util.List;

import et.po.OperBookMedicinfo;
import et.po.OperCaseinfo;
import et.po.OperCorpinfo;
import et.po.OperFocusinfo;
import et.po.OperInquiryCard;
import et.po.OperInquiryinfo;
import et.po.OperMarkanainfo;
import et.po.OperMedicinfo;
import et.po.OperPriceinfo;
import et.po.OperSadinfo;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * ����������ϢΪword�ĵ���txt�ĵ���Excel�ĵ�
 * 
 * @author nie
 * 
 */
public interface OutputService {
	/**
	 * ɾ������Ϣ������������Ϣ��¼�б�
	 * 
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public void delMessagesList(String ids);
	/**
	 * ����ID��ȡ����ع����¼�б�
	 * 
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List<OperSadinfo> getSadList(String ids);
	
	/**
	 * �õ�����׷�ٿ�ļ�¼�б�
	 * @param ids
	 * @return
	 */
	public List<OperFocusinfo> getFocusList(String ids);
	
	/**
	 * �õ���ͨҽ�ƿ��¼��Ϣ
	 * @param ids
	 * @return
	 */
	public List<OperMarkanainfo> getMarkList(String ids);
	
	/**
	 * �õ���ͨҽ�ƿ��¼��Ϣ
	 * @param ids
	 * @return
	 */
	public List<OperCorpinfo> getCrop(String ids);
	
	/**
	 * �õ���ͨҽ�ƿ��¼��Ϣ
	 * @param ids
	 * @return
	 */
	public List<OperMedicinfo> getMedical(String ids);
	
	/**
	 * �õ���ͨҽ�ƿ��¼��Ϣ
	 * @param ids
	 * @return
	 */
	public List<OperBookMedicinfo> getbookMedical(String ids);
	/**
	 * ����ID��ȡ����ع����¼�б�ת��dto�������
	 * 
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List<DynaBeanDTO> getSadList2(String ids);
	/**
	 * ����ID��ȡ����ذ�����¼�б�
	 * 
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List<OperCaseinfo> getCaseList(String ids);
	/**
	 * ����ID��ȡ���۸���е���ؼ�¼�б�
	 * 
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List<OperPriceinfo> getPriceList(String ids);
	/**
	 * @author wwq
	 * ����ID�õ��г��������б�
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List<OperMarkanainfo> getMarkanaList(String ids);
	/**
	 * @author wwq
	 * ����ID�õ�����׷�ٿ��б�
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List<OperFocusinfo> getTraceList(String ids);
	/**
	 * ����������Ϣ��word�ĵ�
	 * 
	 * @param pos ������Ϣ����,ÿ��������Ϣ��һ��OperCaseinfo����
	 * @param fileName �ļ�·�����ļ���
	 */
	public void outputWordFile(List<OperCaseinfo> pos, String fileName, String dbType);

	/**
	 * ����������Ϣ��txt�ĵ�
	 * 
	 * @param pos ������Ϣ����,ÿ��������Ϣ��һ��OperCaseinfo�������
	 * @param fileName ���ɵ��ļ�·�����ļ���
	 */
	public void outputTxtFile(List<OperCaseinfo> pos, String fileName, String dbType);

	/**
	 * ����������Ϣ��Excel�ĵ�
	 * 
	 * @param pos ������Ϣ����,ÿ��������Ϣ��һ��OperCaseinfo�������
	 * @param fileName ���ɵ��ļ�·�����ļ���
	 */
	public void outputExcelFile(List pos, String fileName, String dbType);
	
	/**
	 * zhang feng add(˼·����ɾ��)
	 * ����ģ�嵼��excel
	 * @param pos �б���Ϣ
	 * @param fileName �ļ���
	 * @param dbType ����
	 */
//	public void outputExcel(List pos, String fileName, String dbType);
	
	/**
	 * ����ID��ȡ��������Ϣ������е���ؼ�¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List getInquiryResultList(String ids);
	
	/**
	 * ����ID��ȡ��������Ϣ�������е���ؼ�¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List getInquiryResult2List(String ids);
	
	/**
	 * ����ID��ȡ��������Ϣ������е���ؼ�¼�б�
	 * 
	 * @param ids
	 *            ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List<OperInquiryinfo> getInquiryCardList(String ids);
	
	/**
	 * ����IDȡ��OperInquiryCard��ʵ��
	 * 
	 * @param id OperInquiryInfo��id
	 * @return oic ����ʵ��
	 */
	public List getCard(String id);
	
	/**
	 * ����ID��ȡ��������Ϣ�������еĵ�������¼�б�
	 * 
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List getDictInquiryType(String ids);
	
	/**
	 * ����ID��ȡ��������Ϣ�������е���ؼ�¼�б�
	 * 
	 * @param ids ���ID�м��ö��Ÿ������ַ���
	 * @return list �������ݺ�dto�б�
	 */
	public List getResultList(String ids);
	


}

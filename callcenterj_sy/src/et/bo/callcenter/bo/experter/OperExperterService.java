/**
 * className OperExperterService 
 * 
 * �������� 2008-5-12
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.callcenter.bo.experter;

import java.util.List;

import et.po.OperCustinfo;

/**
 * ����ר����Ϣ
 * @version 	2008-05-06
 * @author ����Ȩ
 */
public interface OperExperterService {
	/**
	 * �õ����е�ר���б�
	 * @return List operCustinfoList
	 */
	List<OperCustinfo> getOperCustinfoList();
	/**
	 * �õ����е�ר���б��ַ��� ר������ �͵绰 ר���� A��@С��:23834132,С��:13998823514;B��@С��:8002;
	 * @return String ר������:�绰,ר������:�绰; 
	 */
	String getOperCustinfoStrs();
	/**
	 * �õ����е�ר���б��ַ��� ר��ID ��NAME
	 * @return String ID#NAME,ID#NAME; 
	 */
	String getOperExperterIDNameStrs();
}

/**
 * 	@(#)PoliceInfoService.java   Oct 10, 2006 9:08:38 AM
 *	 �� 
 *	 
 */
package et.bo.pcc.policeinfo;

import java.util.List;

import et.bo.pcc.policeinfo.impl.PoliceCallInInfoInMemory;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 10, 2006
 * @see
 */
public interface PoliceInfoService {
	
	/**
	 * ��龯���Ƿ����
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return �����Ƿ�ɹ�
	 */
	public boolean checkPoliceNum(String policeNum,String password);
	
	/**
	 * ȷ�ϴ˾�����Ա��Ϣ�Ƿ��ʵ
	 * @param dto IBaseDTO
	 * @version Oct 10, 2006
	 * @return ���ؼ���Ƿ���ʵ
	 */
	public boolean checkMorePolice(IBaseDTO dto);
	
	/**
	 * �õ�������Ա��ϸ��Ϣ
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return ���ؾ�����Ա��ϸ��Ϣ
	 */
	public IBaseDTO getPoliceInfo(String policeNum);
	
	/**
	 * ��������ѯ��¼(д���ڴ�map)
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public boolean addPoliceCallInInfo(PoliceCallInInfoInMemory pm);
	
	/**
	 * �����������ݿ�
	 * @param policeCallInInfo List
	 * @version Oct 10, 2006
	 * @return
	 */
	public boolean addBatchPoliceCallInInfo(List policeCallInInfo);
	
	/**
	 * ��ѯ������Ա�����¼��Ϣ
	 * @param dto ���� IBaseDTO ������Ա��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List ���ؾ�����Ա�б���Ϣ
	 */
	public List callInInfoIndex(String pocid);
	
	/**
	 * �õ��ڴ��е�id�Ÿ�����ϯ��
	 * @param
	 * @version Oct 14, 2006
	 * @return
	 */
	public String getPoliceIdByOpNum(String operatornum);
	
	/**
	 * ��temp������ɾ��,��ֵ����ʵ�����ݱ�
	 * @param dto ���� IBaseDTO ������Ա��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List ���ؾ�����Ա�б���Ϣ
	 */
	public boolean finishOper(String pocid);
	
	public void upTable(String pocid);
	
	public boolean insertValue(String pocid);
	
	public IBaseDTO getQuestionInfo(String id);
	
	/**
	 * ��policecallin�е�ֵ
	 * @param
	 * @version Dec 7, 2006
	 * @return
	 */
	public void transactBefore();
}

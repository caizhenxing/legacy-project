/**
 * 	@(#)PhoneInfoService.java   Nov 7, 2006 9:35:40 AM
 *	 �� 
 *	 
 */
package et.bo.pcc.phoneinfo;

import java.util.List;

import et.bo.pcc.policeinfo.impl.PoliceCallInInfoInMemory;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Nov 7, 2006
 * @see
 */
public interface PhoneInfoService {
	
	/**
	 * ��龯���Ƿ����
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return �����Ƿ�ɹ�
	 */
	public boolean checkPoliceNum(String policeNum,String password);
	
	/**
	 * �õ�������Ա��ϸ��Ϣ
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return ���ؾ�����Ա��ϸ��Ϣ
	 */
	public IBaseDTO getPoliceInfo(String policeNum);
	
	/**
	 * ��Ӿ�����Ա��Ϣ������(��������)
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public String addPoliceCallin(IBaseDTO dto);
	
	/**
	 * ���뾯Ա������Ϣ
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public boolean addPoliceCallInInfo(PoliceCallInInfoInMemory pm);
	
	/**
	 * ��ѯ������Ա�����¼��Ϣ
	 * @param dto ���� IBaseDTO ������Ա��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List ���ؾ�����Ա�б���Ϣ
	 */
	public List callInInfoIndex(String pocid,String fuzznum);
	
	/**
	 * �õ���Աѯ��������Ϣ
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public IBaseDTO getQuestionInfo(String id);
	
	/**
	 * ����cclog��Ϣ
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public String saveCclog(String phonenum);
	
	/**
	 * �޸�cclog��Ϣ
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public void upCclog(String id);
	
	/**
	 * 
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public void saveCclogEnd(String phonenum);
}

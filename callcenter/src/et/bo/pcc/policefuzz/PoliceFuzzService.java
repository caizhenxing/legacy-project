/**
 * 	@(#)PoliceFuzzService.java   Oct 9, 2006 2:18:45 PM
 *	 �� 
 *	 
 */
package et.bo.pcc.policefuzz;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 9, 2006
 * @see
 */
public interface PoliceFuzzService {
	
	/**
	 * ¼�뾯����Ա��Ϣ
	 * @param dto ���� IBaseDTO ������Ա��Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addFuzzInfo(IBaseDTO dto);
	
	/**
	 * �޸ľ�����Ա��Ϣ
	 * @param dto ���� IBaseDTO ������Ա��Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean updateFuzzInfo(IBaseDTO dto);
	
	/**
	 * ɾ��������Ա��Ϣ
	 * @param dto ���� IBaseDTO ������Ա��Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean delFuzzInfo(IBaseDTO dto);
	
	/**
	 * �õ�������Ա��Ϣ
	 * @param id ���� String ������Ա
	 * @version Aug 31, 2006
	 * @return
	 */
	public IBaseDTO getFuzzInfo(String id);
	
	
	/**
	 * �õ�������Ա��ϸ��Ϣ
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return ���ؾ�����Ա��ϸ��Ϣ
	 */
	public IBaseDTO getPoliceInfo(String policeNum);
	/**
	 * ��ѯ������Ա��Ϣ
	 * @param dto ���� IBaseDTO ������Ա��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List ���ؾ�����Ա�б���Ϣ
	 */
	public List fuzzIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getFuzzSize();
	
	/**
	 * ��龯���Ƿ����
	 * @param policeNum String
	 * @version Oct 11, 2006
	 * @return true ���� false ������
	 */
	public boolean checkPoliceNum(String policeNum);
	
	/**
	 * �޸ľ���
	 * @param policeNum String
	 * @version Oct 11, 2006
	 * @return true �޸ĳɹ� false �޸�ʧ��
	 */
	public boolean updatePoliceNum(IBaseDTO dto);
	
	//�����Ϣ
	public void addPoc(et.po.PoliceFuzzInfo pf);
	
	/**
	 * ��ѯ������Ա��Ϣ
	 * @param dto ���� IBaseDTO ������Ա��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List ���ؾ�����Ա�б���Ϣ
	 */
	public List countIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getCountSize();
	
	/**
	 * �õ�Զ���ļ������ӵ�ַ
	 * @param
	 * @version Nov 23, 2006
	 * @return
	 */
	public String getRemoateFile(IBaseDTO dto) throws FileNotFoundException,IOException, RowsExceededException, WriteException;
	
}

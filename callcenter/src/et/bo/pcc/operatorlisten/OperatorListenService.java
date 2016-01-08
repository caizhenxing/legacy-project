/**
 * 	@(#)OperatorListen.java   Oct 8, 2006 3:35:20 PM
 *	 �� 
 *	 
 */
package et.bo.pcc.operatorlisten;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 8, 2006
 * @see
 */
public interface OperatorListenService {
	
	/**
	 * ��ѯ��ϯ�����Ϣ
	 * @param dto ���� IBaseDTO ��ϯ��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List operatorListenIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getOperatorListenSize();
	
	/**
	 * ��ѯ����Ա�б� 
	 * IBaseDTO
	 * @param
	 * @version Sep 12, 2006
	 * @return
	 */
	public List<LabelValueBean> userlist();
	
}

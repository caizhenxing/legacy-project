/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-7
 */
package et.bo.callcenter.appraise.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 
 * @author chen gang
 *
 */
public interface AppraiseService {
	
	/**
	 * ��ѯ�����б�
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List appQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * �������ۼ�¼��Ŀ
	 * @return
	 */
	public int getAppSize();
	
	/**
	 * ����������б�
	 */
	public List userQuery();
}
